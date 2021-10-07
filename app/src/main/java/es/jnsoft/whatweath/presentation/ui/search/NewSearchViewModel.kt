package es.jnsoft.whatweath.presentation.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.SettingsRepository
import es.jnsoft.domain.usecase.FindCurrentByNameUseCase
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.mapper.toPresentation
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewSearchViewModel @Inject constructor(
    private val findCurrentByNameUseCase: FindCurrentByNameUseCase,
    settingsRepository: SettingsRepository
) : ViewModel() {

    private val domainData = MutableStateFlow<Result<Current>?>(null)

    private val units = settingsRepository.getUnits()

    val presentationData: Flow<Result<CurrentPresentation>?> = combine(domainData, units) { resultSearch, selectedUnits ->
        when (resultSearch) {
            is Result.Success -> Result.Success(resultSearch.value.toPresentation(selectedUnits))
            is Result.Loading -> Result.Loading
            is Result.Failure -> {
                sendEvent(Event.ShowSnackbarString(resultSearch.message))
                Result.Failure(resultSearch.message)
            }
            else -> null
        }
    }

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun findByName(name: String) {
        viewModelScope.launch {
            if (name.length < 3) {
                sendEvent(Event.ShowSnackbarResource(R.string.search_min_characters))
            } else {
                domainData.value = Result.Loading
                domainData.value = findCurrentByNameUseCase.invoke(name)
            }
        }
    }

    fun sendEvent(event: Event) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    sealed class Event {
        data class ShowSnackbarResource(val resource: Int): Event()
        data class ShowSnackbarString(val message: String): Event()
        object Clean: Event()
    }
}