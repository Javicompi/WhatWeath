package es.jnsoft.domain.repository

import es.jnsoft.domain.model.Hourly
import es.jnsoft.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface HourlyRepository {

    fun getHourlies(lat: Double, lon: Double): Flow<List<Hourly>>

    suspend fun saveHourlies(hourlies: List<Hourly>)

    suspend fun deleteHourlies(hourlies: List<Hourly>)

    suspend fun findHourlies(lat: Double, lon: Double): Result<List<Hourly>>

    suspend fun updateHourlies(lat: Double, lon: Double)

    fun shouldUpdate(deltaTime: Long): Boolean
}