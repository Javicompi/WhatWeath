package es.jnsoft.domain.repository

import es.jnsoft.domain.model.Daily
import es.jnsoft.domain.model.Location
import es.jnsoft.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface DailyRepository {

    fun getDailies(lat: Double, lon: Double): Flow<List<Daily>>

    suspend fun saveDailies(dailies: List<Daily>)

    suspend fun deleteDailies(location: Location)

    suspend fun findDailies(lat: Double, lon: Double): Result<List<Daily>>

    suspend fun updateDailies(lat: Double, lon: Double)

    fun shouldUpdate(deltaTime: Long): Boolean
}