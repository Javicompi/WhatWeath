package es.jnsoft.data.source

import es.jnsoft.data.local.HourlyLocalDataSource
import es.jnsoft.data.model.HourlyData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeHourlyLocalDataSource : HourlyLocalDataSource {

    private var savedHourlies: MutableList<HourlyData> = mutableListOf()

    override fun getHourlies(cityId: Long): Flow<List<HourlyData>> {
        return flow { emit(savedHourlies.filter { it.cityId == cityId }) }
    }

    override suspend fun saveHourlies(hourlies: List<HourlyData>) {
        savedHourlies = savedHourlies.filter { it.cityId != hourlies[0].cityId }.toMutableList()
        savedHourlies.addAll(hourlies)
    }

    override suspend fun deleteHourlies(cityId: Long) {
        savedHourlies = savedHourlies.filter { it.cityId != cityId }.toMutableList()
    }

    fun clearData() {
        savedHourlies.clear()
    }
}