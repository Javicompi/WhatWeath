package es.jnsoft.whatweath.presentation.ui.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jnsoft.domain.enums.Units
import es.jnsoft.domain.usecase.*
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
    getSelectedIdUseCase: GetSelectedIdUseCase,
    getCurrentsUseCase: GetCurrentsUseCase,
    private val setSelectedIdUseCase: SetSelectedIdUseCase,
    getUnitsUseCase: GetUnitsUseCase,
    private val setUnitsUseCase: SetUnitsUseCase
) : BaseViewModel(getUnitsUseCase) {

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
            Log.d("MainViewModel", "Selected Id: $id")
            setSelectedIdUseCase.invoke(id)
        }
    }

    fun setUnits(units: Units) {
        viewModelScope.launch {
            Log.d("MainViewModel", "Units changed to: $units")
            setUnitsUseCase.invoke(units)
        }
    }
}