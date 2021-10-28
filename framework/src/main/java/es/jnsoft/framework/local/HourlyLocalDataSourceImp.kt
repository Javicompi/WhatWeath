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

    override fun getHourlies(cityId: Long): Flow<List<HourlyData>> {
        return hourlyDao.getHourlies(cityId).transform { hourlies ->
            listOf<HourlyData>().mapFromEntityList(hourlies)
        }
    }

    override suspend fun saveHourlies(hourlies: List<HourlyData>) {
        hourlyDao.updateHourlies(hourlies.mapToEntityList())
    }

    override suspend fun deleteHourlies(cityId: Long) {
        hourlyDao.deleteHourlies(cityId)
    }
}