package es.jnsoft.data.remote

import es.jnsoft.data.model.DailyData
import es.jnsoft.domain.model.Result

interface DailyRemoteDataSource {

    suspend fun findDaily(lat: Double, lon: Double): Result<List<DailyData>>
}