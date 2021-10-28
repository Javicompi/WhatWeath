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

    override fun getHourlies(cityId: Long): Flow<List<Hourly>> {
        return flow {
            val filteredHourlies = hourlies.filter { it.cityId == cityId }
            emit(filteredHourlies)
            if (filteredHourlies.isNotEmpty() && shouldUpdate(filteredHourlies[0].deltaTime)) {
                val newHourlies = filteredHourlies.map { hourly ->
                    hourly.copy(deltaTime = hourly.deltaTime + 3600000)
                }
                hourlies.removeAll { it.cityId == cityId }
                hourlies.addAll(newHourlies)
                emit(hourlies.filter { it.cityId == cityId })
            }
        }
    }

    override suspend fun saveHourlies(hourlies: List<Hourly>) {
        if (this.hourlies.isNotEmpty()) {
            val cityId = hourlies[0].cityId
            this.hourlies.removeAll { it.cityId == cityId }
        }
        this.hourlies.addAll(hourlies)
    }

    override suspend fun deleteHourlies(cityId: Long) {
        val newHourlies = mutableListOf<Hourly>()
        hourlies.forEach { hourly ->
            if (hourly.cityId != cityId) newHourlies.add(hourly)
        }
        hourlies = newHourlies
    }

    override suspend fun findHourlies(lat: Double, lon: Double): Result<List<Hourly>> {
        return if (lat > 1.0 && lon > 1.0) {
            Result.Success(createHourlyList(6697298))
        } else {
            Result.Failure("Location not found")
        }
    }

    override fun shouldUpdate(deltaTime: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return currentTime - deltaTime >= TimeUnit.HOURS.toMillis(1)
    }
}