package es.jnsoft.whatweath.presentation.ui.base

import androidx.lifecycle.ViewModel
import es.jnsoft.usecase.GetCurrentByIdUseCase
import es.jnsoft.usecase.GetSelectedIdUseCase
import es.jnsoft.usecase.GetUnitsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest

@ExperimentalCoroutinesApi
abstract class BaseViewModel(
    getUnitsUseCase: GetUnitsUseCase,
    getSelectedIdUseCase: GetSelectedIdUseCase,
    private val getCurrentByIdUseCase: GetCurrentByIdUseCase
) : ViewModel() {

    protected val units = getUnitsUseCase(Unit)

    val currentDomain = getSelectedIdUseCase(Unit).flatMapLatest { id ->
        getCurrentByIdUseCase(id)
    }
}