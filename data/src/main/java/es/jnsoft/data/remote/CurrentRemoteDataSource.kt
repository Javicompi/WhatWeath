package es.jnsoft.data.remote

import es.jnsoft.data.model.CurrentData
import es.jnsoft.domain.model.Result

interface CurrentRemoteDataSource {

    suspend fun findCurrentByLatLon(lat: Double, lon: Double): Result<CurrentData>

    suspend fun findCurrentByName(name: String): Result<CurrentData>

    suspend fun findCurrentById(id: Long): Result<CurrentData>
}