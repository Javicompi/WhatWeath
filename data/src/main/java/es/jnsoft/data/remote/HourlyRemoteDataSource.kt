package es.jnsoft.data.remote

import es.jnsoft.data.model.HourlyData
import es.jnsoft.domain.model.Result

interface HourlyRemoteDataSource {

    suspend fun findHourly(lat: Double, lon: Double): Result<List<HourlyData>>
}