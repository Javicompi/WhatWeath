package es.jnsoft.domain.repository

import es.jnsoft.domain.createHourlyList
import es.jnsoft.domain.model.Hourly
import es.jnsoft.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit

class FakeHourlyRepository : HourlyRepository {

    private var hourlies: MutableList<Hourly> = mutableListOf()

    fun setData(list: MutableList<Hourly>) {
        hourlies = list
    }

    fun getData() = hourlies

    override fun getHourlies(lat: Double, lon: Double): Flow<List<Hourly>> {
        return flow {
            val filteredHourlies = hourlies.filter { it.location.lat == lat && it.location.lon == lon }
            emit(filteredHourlies)
            if (filteredHourlies.isNotEmpty() && shouldUpdate(filteredHourlies[0].deltaTime)) {
                val newHourlies = filteredHourlies.map { hourly ->
                    hourly.copy(deltaTime = hourly.deltaTime + 3600000)
                }
                hourlies.removeAll { it.location.lat == lat && it.location.lon == lon }
                hourlies.addAll(newHourlies)
                emit(hourlies.filter { it.location.lat == lat && it.location.lon == lon })
            }
        }
    }

    override suspend fun saveHourlies(hourlies: List<Hourly>) {
        if (this.hourlies.isNotEmpty()) {
            val lat = hourlies[0].location.lat
            val lon = hourlies[0].location.lon
            this.hourlies.removeAll { it.location.lat == lat && it.location.lon == lon }
        }
        this.hourlies.addAll(hourlies)
    }

    override suspend fun deleteHourlies(hourlies: List<Hourly>) {
        val lat = hourlies[0].location.lat
        val lon = hourlies[0].location.lon
        val newHourlies = this.hourlies.filter { it.location.lat != lat && it.location.lon != lon }
        /*hourlies.forEach { hourly ->
            if (hourly.location.lat != lat && hourly.location.lon != lon) newHourlies.add(hourly)
        }*/
        this.hourlies = newHourlies.toMutableList()
    }

    override suspend fun findHourlies(lat: Double, lon: Double): Result<List<Hourly>> {
        return if (lat > 1.0 && lon > 1.0) {
            Result.Success(createHourlyList(lat, lon))
        } else {
            Result.Failure("Location not found")
        }
    }

    override fun shouldUpdate(deltaTime: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return currentTime - deltaTime >= TimeUnit.HOURS.toMillis(1)
    }
}