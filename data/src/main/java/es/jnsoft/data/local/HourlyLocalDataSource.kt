package es.jnsoft.data.local

import es.jnsoft.data.model.HourlyData
import kotlinx.coroutines.flow.Flow

interface HourlyLocalDataSource {

    fun getHourlies(cityId: Long): Flow<List<HourlyData>>

    suspend fun saveHourlies(hourlies: List<HourlyData>)

    suspend fun deleteHourlies(cityId: Long)
}