package es.jnsoft.whatweath.presentation.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import es.jnsoft.domain.enums.Units
import es.jnsoft.domain.usecase.GetUnitsUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<DomainData, PresentationData>(
    getUnitsUseCase: GetUnitsUseCase
) : ViewModel() {

    protected val _domainData = MutableStateFlow<DomainData?>(null)
    val domainData: StateFlow<DomainData?> = _domainData

    private val eventChannel = Channel<Event>(Channel.BUFFERED)
    val eventsFlow = eventChannel.receiveAsFlow()

    fun sendEvent(event: Event) {
        viewModelScope.launch {
            eventChannel.send(event)
        }
    }

    private val units = getUnitsUseCase.invoke(Unit)

    val presentation = combine(_domainData, units) { domainData, units ->
        mapToPresentation(domainData = domainData, units = units)
    }

    abstract fun mapToPresentation(domainData: DomainData?, units: Units): PresentationData?

    sealed class Event {
        data class NavigateTo(val destination: String): Event()
        data class ShowSnackbarResource(val resource: Int): Event()
        data class ShowSnackbarString(val message: String): Event()
        object Clean: Event()
    }
}