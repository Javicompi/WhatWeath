package es.jnsoft.whatweath.presentation.ui.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jnsoft.domain.model.Units
import es.jnsoft.usecase.*
import es.jnsoft.whatweath.presentation.mapper.toPresentation
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import es.jnsoft.whatweath.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@ExperimentalCoroutinesApi
class MainViewModel @Inject constructor(
    private val setSelectedIdUseCase: SetSelectedIdUseCase,
    private val setUnitsUseCase: SetUnitsUseCase,
    getSelectedIdUseCase: GetSelectedIdUseCase,
    getCurrentsUseCase: GetCurrentsUseCase,
    getCurrentByIdUseCase: GetCurrentByIdUseCase,
    getUnitsUseCase: GetUnitsUseCase,
) : BaseViewModel(getUnitsUseCase, getSelectedIdUseCase, getCurrentByIdUseCase) {

    val selectedId: StateFlow<Long> = getSelectedIdUseCase.invoke(Unit).stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0L
    )

    private val currentsDomain = getCurrentsUseCase.invoke(Unit)

    val currentsPresentation: StateFlow<List<CurrentPresentation>> =
        combine(currentsDomain, units) { currents, selectedUnits ->
            currents.map { it.toPresentation(selectedUnits) }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = listOf()
        )

    fun setSelectedId(id: Long) {
        viewModelScope.launch {
            setSelectedIdUseCase(id)
        }
    }

    fun setUnits(units: Units) {
        viewModelScope.launch {
            setUnitsUseCase(units)
        }
    }

    fun onStart() {
        viewModelScope.launch {
            currentsDomain.collect()
        }
    }
}