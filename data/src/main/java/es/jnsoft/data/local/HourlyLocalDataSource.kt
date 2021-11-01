package es.jnsoft.data.local

import es.jnsoft.data.model.HourlyData
import kotlinx.coroutines.flow.Flow

interface HourlyLocalDataSource {

    fun getHourlies(lat: Double, lon: Double): Flow<List<HourlyData>>

    suspend fun saveHourlies(hourlies: List<HourlyData>)

    suspend fun deleteHourlies(hourlies: List<HourlyData>)
}