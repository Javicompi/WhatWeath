package es.jnsoft.framework.datasource.local

import es.jnsoft.framework.local.dao.HourlyDao
import es.jnsoft.framework.local.model.HourlyEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeHourlyDao : HourlyDao {

    private var savedHourlies: MutableList<HourlyEntity> = mutableListOf()

    override fun getHourlies(lat: Double, lon: Double): Flow<List<HourlyEntity>> {
        return flow {
            val hourlies = savedHourlies.filter { it.latitude == lat && it.longitude == lon }
            emit(hourlies)
        }
    }

    override suspend fun saveHourlies(hourlies: List<HourlyEntity>) {
        if (savedHourlies.isNotEmpty()) {
            val lat = hourlies[0].latitude
            val lon = hourlies[0].longitude
            savedHourlies.removeIf { it.latitude == lat && it.longitude == lon }
        }
        savedHourlies.addAll(hourlies)
    }

    override suspend fun deleteHourlies(lat: Double, lon: Double) {
        savedHourlies.removeAll { it.latitude == lat && lon == lon }
    }
}