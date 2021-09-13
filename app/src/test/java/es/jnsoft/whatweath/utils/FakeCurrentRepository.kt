package es.jnsoft.whatweath.utils

import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.CurrentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map

class FakeCurrentRepository : CurrentRepository {

    private var currentsFlow: MutableStateFlow<List<Current>> = MutableStateFlow(listOf())

    fun setData(list: MutableList<Current>) {
        currentsFlow.value = list
    }

    override suspend fun getCurrents(): Flow<List<Current>> {
        return currentsFlow
    }

    override suspend fun getCurrentById(id: Long): Flow<Current?> {
        return currentsFlow.map { list ->
            list.firstOrNull { it.id == id }
        }
    }

    override suspend fun saveCurrent(current: Current) {
        val currents = currentsFlow.value.toMutableList()
        currents.forEachIndexed { index, saved ->
            saved.takeIf { it.id == current.id }?.let {
                currents[index] = current
                refreshCurrents(currents)
                return
            }
        }
        currents.add(current)
        refreshCurrents(currents)
    }

    override suspend fun deleteCurrent(current: Current) {
        val currents = currentsFlow.value.toMutableList()
        currents.remove(current)
        refreshCurrents(currents)
    }

    override suspend fun findCurrentByLatLon(lat: Double, lon: Double): Result<Current> {
        return if (lat > 0.0 && lon > 0.0) {
            Result.Success(createFirstCurrent())
        } else {
            Result.Failure("Location not found")
        }
    }

    override suspend fun findCurrentByName(name: String): Result<Current> {
        return if (name.isNotBlank()) {
            Result.Success(createFirstCurrent())
        } else {
            Result.Failure("Location not found")
        }
    }

    private fun refreshCurrents(currents: MutableList<Current>) {
        currentsFlow.value = currents
    }
}