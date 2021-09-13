package es.jnsoft.whatweath.presentation.mapper

internal interface PresentationMapper<Domain, out BasePresentation, Units> {

    fun mapToPresentation(domain: Domain, units: Units): BasePresentation
}