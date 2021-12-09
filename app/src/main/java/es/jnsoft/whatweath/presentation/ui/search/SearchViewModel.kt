package es.jnsoft.whatweath.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.SettingsRepository
import es.jnsoft.domain.usecase.*
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.mapper.toPresentation
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import es.jnsoft.whatweath.presentation.model.HourlyPresentation
import es.jnsoft.whatweath.utils.createDaily
import es.jnsoft.whatweath.utils.createHourly
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class SearchViewModel @Inject constructor(
    private val findCurrentByNameUseCase: FindCurrentByNameUseCase,
    private val findHourliesUseCase: FindHourliesUseCase,
    private val saveCurrentUseCase: SaveCurrentUseCase,
    private val saveHourliesUseCase: SaveHourliesUseCase,
    private val saveDailiesUseCase: SaveDailiesUseCase,
    private val setSelectedIdUseCase: SetSelectedIdUseCase,
    settingsRepository: SettingsRepository
) : ViewModel() {

    private val units = settingsRepository.getUnits()

    private val currentDomain = MutableStateFlow<Result<Current>>(Result.Failure(""))

    private val hourlyDomain = currentDomain.flatMapLatest { current ->
        flow {
            if (current is Result.Success) {
                emit(Result.Loading)
                emit(findHourliesUseCase.invoke(current.value.location))
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = Result.Loading
    )

    val currentPresentation: StateFlow<Result<CurrentPresentation>> =
        combine(currentDomain, units) { resultSearch, selectedUnits ->
            when (resultSearch) {
                is Result.Success -> {
                    Result.Success(resultSearch.value.toPresentation(selectedUnits))
                }
                is Result.Loading -> Result.Loading
                is Result.Failure -> {
                    if (resultSearch.message.isNotEmpty()) {
                        sendEvent(Event.ShowSnackbarString(resultSearch.message))
                    }
                    Result.Failure(resultSearch.message)
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Failure("")
        )

    val hourlyPresentation: StateFlow<Result<List<HourlyPresentation>>> =
        combine(hourlyDomain, units) { resultSearch, selectedUnits ->
            when (resultSearch) {
                is Result.Success -> {
                    Result.Success(resultSearch.value.subList(1, 25).map { hourly ->
                        hourly.toPresentation(selectedUnits)
                    })
                }
                is Result.Loading -> Result.Loading
                is Result.Failure -> {
                    Result.Failure(resultSearch.message)
                }
                else -> Result.Loading
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun findByName(name: String) {
        viewModelScope.launch {
            if (name.length < 3) {
                sendEvent(Event.ShowSnackbarResource(R.string.search_min_characters))
            } else {
                currentDomain.value = Result.Loading
                currentDomain.value = findCurrentByNameUseCase.invoke(name)
            }
        }
    }

    fun saveData() {
        viewModelScope.launch {
            val current = currentDomain.value
            if (current is Result.Success) {
                saveCurrentUseCase.invoke(current.value)
                val lat = current.value.location.lat
                val lon = current.value.location.lon
                val hourlies = hourlyDomain.value
                if (hourlies is Result.Success && hourlies.value.isNotEmpty()) {
                    saveHourliesUseCase.invoke(hourlies.value)
                } else {
                    saveHourliesUseCase.invoke(listOf(createHourly(lat, lon)))
                }
                val dailies = createDaily(lat, lon)
                saveDailiesUseCase.invoke(listOf(dailies))
                setSelectedIdUseCase.invoke(current.value.id)
                sendEvent(Event.NavigateToCurrent)
            }
        }
    }

    fun sendEvent(event: Event) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    sealed class Event {
        data class ShowSnackbarResource(val resource: Int) : Event()
        data class ShowSnackbarString(val message: String) : Event()
        object NavigateToCurrent : Event()
        object Clean : Event()
    }
}