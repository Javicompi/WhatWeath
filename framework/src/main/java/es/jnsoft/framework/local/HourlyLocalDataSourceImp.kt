package es.jnsoft.framework.local

import es.jnsoft.data.local.HourlyLocalDataSource
import es.jnsoft.data.model.HourlyData
import es.jnsoft.framework.local.dao.HourlyDao
import es.jnsoft.framework.mapper.mapFromEntityList
import es.jnsoft.framework.mapper.mapToEntityList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HourlyLocalDataSourceImp @Inject constructor(
    private val hourlyDao: HourlyDao
) : HourlyLocalDataSource {

    override fun getHourlies(lat: Double, lon: Double): Flow<List<HourlyData>> {
        return hourlyDao.getHourlies(lat, lon).map { hourlies ->
            listOf<HourlyData>().mapFromEntityList(hourlies)
        }
    }

    override suspend fun saveHourlies(hourlies: List<HourlyData>) {
        hourlyDao.saveHourlies(hourlies.mapToEntityList())
    }

    override suspend fun deleteHourlies(lat: Double, lon: Double) {
        hourlyDao.deleteHourlies(lat = lat, lon = lon)
    }

    override suspend fun updateHourlies(hourlies: List<HourlyData>) {
        hourlyDao.updateHourlies(hourlies.mapToEntityList())
    }
}