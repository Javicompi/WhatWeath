package es.jnsoft.whatweath.presentation.ui.days

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jnsoft.domain.model.Daily
import es.jnsoft.domain.model.Result
import es.jnsoft.usecase.*
import es.jnsoft.whatweath.presentation.mapper.toPresentation
import es.jnsoft.whatweath.presentation.model.DailyPresentation
import es.jnsoft.whatweath.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DaysViewModel @Inject constructor(
    private val getDailiesUseCase: GetDailiesUseCase,
    private val deleteEntryUseCase: DeleteEntryUseCase,
    getCurrentByIdUseCase: GetCurrentByIdUseCase,
    getUnitsUseCase: GetUnitsUseCase,
    getSelectedIdUseCase: GetSelectedIdUseCase
) : BaseViewModel(getUnitsUseCase, getSelectedIdUseCase, getCurrentByIdUseCase) {

    private val dailiesDomain = currentDomain.flatMapLatest { current ->
        flow {
            current?.let { emitAll(getDailiesUseCase(it.location)) } ?: listOf<Daily>()
        }
    }

    val dailyPresentation: StateFlow<Result<List<DailyPresentation>>> =
        combine(dailiesDomain, units) { dailies, selectedUnits ->
            if (dailies.isNullOrEmpty()) {
                Result.Failure("")
            } else {
                Result.Success(dailies.map { daily ->
                    daily.toPresentation(selectedUnits)
                })
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = Result.Loading
        )

    fun onStart() {
        viewModelScope.launch {
            dailiesDomain.collect()
        }
    }

    fun deleteData() {
        viewModelScope.launch {
            val current = currentDomain.first()
            current?.let { entry ->
                deleteEntryUseCase(entry)
            }
        }
    }
}