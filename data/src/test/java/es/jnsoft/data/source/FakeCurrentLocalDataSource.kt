package es.jnsoft.data.source

import es.jnsoft.data.local.CurrentLocalDataSource
import es.jnsoft.data.model.CurrentData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

class FakeCurrentLocalDataSource : CurrentLocalDataSource {

    private val currents: MutableList<CurrentData> = mutableListOf()

    override fun getCurrents(): Flow<List<CurrentData>>  {
        return flow { emit(currents) }
    }

    override fun getCurrentById(id: Long): Flow<CurrentData?> {
        return flow {
            emit(currents.firstOrNull { it.id == id })
        }
    }

    override suspend fun saveCurrent(current: CurrentData): Unit = withContext(Dispatchers.IO) {
        val position = currents.indexOfFirst {
            it.id == current.id
        }
        if (position >= 0) {
            currents[position] = current
        } else {
            currents.add(current)
        }
    }

    override suspend fun deleteCurrent(current: CurrentData): Unit = withContext(Dispatchers.IO) {
        currents.remove(current)
    }

    fun clearData() {
        currents.clear()
    }
}