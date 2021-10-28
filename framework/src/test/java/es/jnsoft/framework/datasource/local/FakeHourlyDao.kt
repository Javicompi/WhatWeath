package es.jnsoft.framework.datasource.local

import es.jnsoft.framework.local.dao.HourlyDao
import es.jnsoft.framework.local.model.HourlyEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeHourlyDao : HourlyDao {

    private var savedHourlies: MutableList<HourlyEntity> = mutableListOf()

    private var primaryKey = 1L

    override fun getHourlies(cityId: Long): Flow<List<HourlyEntity>> {
        return flow {
            val hourlies = savedHourlies.filter { it.cityId == cityId }
            emit(hourlies)
            //emit(savedHourlies/*.filter { it.cityId == cityId }*/)
        }
    }

    override suspend fun saveHourlies(hourlies: List<HourlyEntity>) {
        /*savedHourlies.removeAll { it.cityId == hourlies[0].cityId }
        val newHourlies: List<HourlyEntity> = hourlies.map { hourly ->
            if (hourly.id == 0L) {
                primaryKey++
                hourly.copy(id = primaryKey)
            } else {
                hourly
            }
        }*/
        savedHourlies.addAll(hourlies)
    }

    override suspend fun deleteHourlies(cityId: Long) {
        savedHourlies.removeAll { it.cityId == cityId }
    }
}