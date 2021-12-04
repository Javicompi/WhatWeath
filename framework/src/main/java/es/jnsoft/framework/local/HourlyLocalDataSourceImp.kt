package es.jnsoft.framework.local

import es.jnsoft.data.local.HourlyLocalDataSource
import es.jnsoft.data.model.HourlyData
import es.jnsoft.framework.local.dao.HourlyDao
import es.jnsoft.framework.mapper.mapFromEntityList
import es.jnsoft.framework.mapper.mapToEntityList
import kotlinx.coroutines.flow.*
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

    override suspend fun deleteHourlies(hourlies: List<HourlyData>) {
        val lat = hourlies[0].lat
        val lon = hourlies[0].lon
        hourlyDao.deleteHourlies(lat = lat, lon = lon)
    }
}