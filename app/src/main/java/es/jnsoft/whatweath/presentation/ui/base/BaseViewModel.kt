package es.jnsoft.whatweath.presentation.ui.base

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.jnsoft.domain.enums.Units
import es.jnsoft.domain.repository.SettingsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.launch

abstract class BaseViewModel<DomainData, PresentationData>(
    settingsRepository: SettingsRepository
) : ViewModel() {

    protected val _domainData = MutableStateFlow<DomainData?>(null)
    val domainData: StateFlow<DomainData?> = _domainData

    protected val errorResourceChannel = Channel<Int>(Channel.BUFFERED)
    val errorResourceFlow = errorResourceChannel.receiveAsFlow().conflate()

    protected fun sendResourceError(@StringRes error: Int) {
        viewModelScope.launch { errorResourceChannel.send(error) }
    }

    protected val errorStringChannel = Channel<String>(Channel.BUFFERED)
    val errorStringFlow = errorStringChannel.receiveAsFlow().conflate()

    protected fun sendStringError(error: String) {
        viewModelScope.launch { errorStringChannel.send(error) }
    }

    protected val units = settingsRepository.getUnits().stateIn(
        scope = viewModelScope,
        started = WhileSubscribed(5000),
        initialValue = Units.STANDARD
    )

    val presentation = combine(_domainData, units) { domainData, units ->
        mapToPresentation(domainData = domainData, units = units)
    }

    abstract fun mapToPresentation(domainData: DomainData?, units: Units): PresentationData?
}