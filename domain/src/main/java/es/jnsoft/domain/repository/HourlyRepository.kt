package es.jnsoft.domain.repository

import es.jnsoft.domain.model.Hourly
import es.jnsoft.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface HourlyRepository {

    fun getHourlies(cityId: Long): Flow<List<Hourly>>

    suspend fun saveHourlies(hourlies: List<Hourly>)

    suspend fun deleteHourlies(cityId: Long)

    suspend fun findHourlies(lat: Double, lon: Double): Result<List<Hourly>>

    fun shouldUpdate(deltaTime: Long): Boolean
}