package es.jnsoft.data.source

import es.jnsoft.data.local.HourlyLocalDataSource
import es.jnsoft.data.model.HourlyData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeHourlyLocalDataSource : HourlyLocalDataSource {

    private var savedHourlies: MutableList<HourlyData> = mutableListOf()

    override fun getHourlies(lat: Double, lon: Double): Flow<List<HourlyData>> {
        return flow {
            val selectedHourlies = savedHourlies.filter { it.lat == lat && it.lon == lon }
            emit(selectedHourlies)
        }
    }

    override suspend fun saveHourlies(hourlies: List<HourlyData>) {
        savedHourlies = savedHourlies.filter { saveds ->
            saveds.lat != hourlies[0].lat && saveds.lon != hourlies[0].lon
        }.toMutableList()
        savedHourlies.addAll(hourlies)
    }

    override suspend fun deleteHourlies(hourlies: List<HourlyData>) {
        savedHourlies = savedHourlies.filter { saveds ->
            saveds.lat != hourlies[0].lat && saveds.lon != hourlies[0].lon
        }.toMutableList()
    }

    override suspend fun updateHourlies(hourlies: List<HourlyData>) {
        val lat = hourlies[0].lat
        val lon = hourlies[0].lon
        savedHourlies = savedHourlies.filter { it.lat == lat && it.lon == lon }.toMutableList()
        savedHourlies.addAll(hourlies)
    }

    fun clearData() {
        savedHourlies.clear()
    }
}