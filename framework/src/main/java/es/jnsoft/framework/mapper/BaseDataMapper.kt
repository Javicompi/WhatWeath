package es.jnsoft.framework.mapper

import es.jnsoft.data.model.BaseData

internal interface BaseDataMapper<Data, Response: Any, Entity: Any> where Data : BaseData {

    fun mapFromResponse(source: Response): Data

    fun mapToEntity(source: Data): Entity

    fun mapFromEntity(source: Entity): Data
}