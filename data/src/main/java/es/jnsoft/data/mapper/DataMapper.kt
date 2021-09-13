package es.jnsoft.data.mapper

internal interface DataMapper<I, O> {

    fun mapToDomain(source: I): O

    fun mapFromDomain(source: O): I
}