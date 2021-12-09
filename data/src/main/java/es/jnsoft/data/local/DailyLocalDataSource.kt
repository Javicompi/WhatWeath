package es.jnsoft.data.local

import es.jnsoft.data.model.DailyData
import es.jnsoft.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface DailyLocalDataSource {

    fun getDailies(lat: Double, lon: Double): Flow<List<DailyData>>

    suspend fun saveDailies(dailies: List<DailyData>)

    suspend fun deleteDailies(location: Location)
}