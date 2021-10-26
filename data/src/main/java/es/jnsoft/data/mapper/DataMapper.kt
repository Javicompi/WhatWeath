package es.jnsoft.data.mapper

internal interface DataMapper<I, O> {

    fun mapToDomain(source: I): O

    fun mapFromDomain(source: O): I

    fun mapToDomainList(source: List<I>): List<O>

    fun mapFromDomainList(source: List<O>): List<I>
}