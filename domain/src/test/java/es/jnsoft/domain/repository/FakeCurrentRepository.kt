package es.jnsoft.domain.repository

import es.jnsoft.domain.createFirstCurrent
import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit

class FakeCurrentRepository : CurrentRepository {

    private var currents: MutableList<Current> = mutableListOf()

    fun setData(list: MutableList<Current>) {
        currents = list
    }

    fun getData() = currents

    override fun getCurrents(): Flow<List<Current>> {
        return flow { emit(currents) }
    }

    override fun getCurrentById(id: Long): Flow<Current?> {
        return flow {
            emit(currents.firstOrNull { it.id == id })
        }
    }

    override suspend fun saveCurrent(current: Current) {
        currents.forEachIndexed { index, saved ->
            saved.takeIf { it.id == current.id }?.let {
                currents[index] = current
                return
            }
        }
        currents.add(current)
    }

    override suspend fun deleteCurrent(current: Current) {
        currents.remove(current)
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

    override fun shouldUpdate(deltaTime: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return currentTime - deltaTime >= TimeUnit.HOURS.toMillis(1)
    }
}