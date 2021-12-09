package es.jnsoft.framework.local

import es.jnsoft.data.local.DailyLocalDataSource
import es.jnsoft.data.model.DailyData
import es.jnsoft.domain.model.Location
import es.jnsoft.framework.local.dao.DailyDao
import es.jnsoft.framework.mapper.mapFromEntityList
import es.jnsoft.framework.mapper.mapToEntityList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DailyLocalDataSourceImp @Inject constructor(
    private val dailyDao: DailyDao
) : DailyLocalDataSource {

    override fun getDailies(lat: Double, lon: Double): Flow<List<DailyData>> {
        return dailyDao.getDailies(lat, lon).map { dailies ->
            listOf<DailyData>().mapFromEntityList(dailies)
        }
    }

    override suspend fun saveDailies(dailies: List<DailyData>) {
        dailyDao.updateDailies(dailies.mapToEntityList())
    }

    override suspend fun deleteDailies(location: Location) {
        dailyDao.deleteDailies(lat = location.lat, lon = location.lon)
    }
}