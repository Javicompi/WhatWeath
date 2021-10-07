package es.jnsoft.whatweath.presentation.ui.search

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.jnsoft.domain.enums.Units
import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.SettingsRepository
import es.jnsoft.domain.usecase.FindCurrentByNameUseCase
import es.jnsoft.whatweath.R
import es.jnsoft.whatweath.presentation.mapper.toPresentation
import es.jnsoft.whatweath.presentation.model.CurrentPresentation
import es.jnsoft.whatweath.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    //private val findCurrentByLatLonUseCase: FindCurrentByLatLonUseCase,
    private val findCurrentByNameUseCase: FindCurrentByNameUseCase,
    settingsRepository: SettingsRepository
) : BaseViewModel<Result<Current>, CurrentPresentation>(settingsRepository) {

    override fun mapToPresentation(
        domainData: Result<Current>?,
        units: Units
    ): CurrentPresentation? {
        when(domainData) {
            is Result.Failure -> {
                sendEvent(Event.ShowSnackbarString(domainData.message))
                return null
            }
            is Result.Success -> {
                domainData.let { data ->
                    return data.value.toPresentation(units = units)
                }
            } else -> {
                return null
            }
        }
    }

    /*fun findByLocation(lat: Double, lon: Double) {
        _domainData.value = Result.Loading
        viewModelScope.launch {
            val result = findCurrentByLatLonUseCase.invoke(Location(lat, lon))
            handleResult(result)
        }
    }*/

    fun findByName(name: String) {
        Log.d("SearchViewModel", "String: $name")
        viewModelScope.launch {
            if (name.length < 3) {
                sendEvent(Event.ShowSnackbarResource(R.string.search_min_characters))
            } else {
                _domainData.value = Result.Loading
                _domainData.value = findCurrentByNameUseCase.invoke(name)
            }
        }
    }
}