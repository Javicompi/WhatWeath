package es.jnsoft.whatweath.presentation.ui.base

import androidx.lifecycle.ViewModel
import es.jnsoft.domain.usecase.GetCurrentByIdUseCase
import es.jnsoft.domain.usecase.GetSelectedIdUseCase
import es.jnsoft.domain.usecase.GetUnitsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest

@ExperimentalCoroutinesApi
abstract class BaseViewModel(
    getUnitsUseCase: GetUnitsUseCase,
    getSelectedIdUseCase: GetSelectedIdUseCase,
    private val getCurrentByIdUseCase: GetCurrentByIdUseCase
) : ViewModel() {

    protected val units = getUnitsUseCase.invoke(Unit)

    val currentDomain = getSelectedIdUseCase.invoke(Unit).flatMapLatest { id ->
        getCurrentByIdUseCase.invoke(id)
    }
}