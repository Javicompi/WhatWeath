package es.jnsoft.whatweath.presentation.ui.base

import androidx.lifecycle.ViewModel
import es.jnsoft.domain.usecase.GetUnitsUseCase


abstract class BaseViewModel(
    getUnitsUseCase: GetUnitsUseCase
) : ViewModel() {

    protected val units = getUnitsUseCase.invoke(Unit)
}