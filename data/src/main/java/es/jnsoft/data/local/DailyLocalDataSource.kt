package es.jnsoft.data.local

import es.jnsoft.data.model.DailyData
import kotlinx.coroutines.flow.Flow

interface DailyLocalDataSource {

    fun getDailies(lat: Double, lon: Double): Flow<List<DailyData>>

    suspend fun saveDailies(dailies: List<DailyData>)

    suspend fun deleteDailies(lat: Double, lon: Double)
}