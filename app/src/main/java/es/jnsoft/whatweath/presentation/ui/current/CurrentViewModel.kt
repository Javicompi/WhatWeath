package es.jnsoft.whatweath.presentation.ui.current

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jnsoft.domain.model.Hourly
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.usecase.*
import es.jnsoft.whatweath.presentation.mapper.toPresentation
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import es.jnsoft.whatweath.presentation.model.HourlyPresentation
import es.jnsoft.whatweath.presentation.ui.base.NewBaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class CurrentViewModel @Inject constructor(
    private val getCurrentByIdUseCase: GetCurrentByIdUseCase,
    private val getHourliesUseCase: GetHourliesUseCase,
    private val deleteCurrentUseCase: DeleteCurrentUseCase,
    private val deleteHourliesUseCase: DeleteHourliesUseCase,
    getUnitsUseCase: GetUnitsUseCase,
    getSelectedIdUseCase: GetSelectedIdUseCase
) : NewBaseViewModel(getUnitsUseCase) {

    private val currentDomain = getSelectedIdUseCase.invoke(Unit).flatMapLatest { id ->
        getCurrentByIdUseCase.invoke(id)
    }

    private val hourlyDomain = currentDomain.flatMapLatest { current ->
        current?.let { getHourliesUseCase.invoke(current.location) } ?: flow { listOf<Hourly>() }
    }

    val currentPresentation: StateFlow<Result<CurrentPresentation>> =
        combine(currentDomain, units) { current, selectedUnits ->
            if (current == null) {
                Result.Failure("")
            } else {
                Result.Success(current.toPresentation(selectedUnits))
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    val hourlyPresentation: StateFlow<Result<List<HourlyPresentation>>> =
        combine(hourlyDomain, units) { hourlies, selectedUnits ->
            if (hourlies.isNullOrEmpty()) {
                Result.Failure("")
            } else {
                Result.Success(hourlies.map { it.toPresentation(selectedUnits) })
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    fun deleteData() {
        viewModelScope.launch {
            val current = currentDomain.first()
            if (current != null) {
                deleteCurrentUseCase.invoke(current)
            }
            val hourlies = hourlyDomain.first()
            if (hourlies.isNotEmpty()) {
                deleteHourliesUseCase.invoke(hourlies)
            }
        }
    }
}