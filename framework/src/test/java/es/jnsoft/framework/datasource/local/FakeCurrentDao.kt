package es.jnsoft.framework.datasource.local

import es.jnsoft.framework.local.dao.CurrentDao
import es.jnsoft.framework.local.model.CurrentEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCurrentDao : CurrentDao {

    private val currents: MutableList<CurrentEntity> = mutableListOf()

    override fun getCurrents(): Flow<List<CurrentEntity>> {
        return flow {
            emit(currents)
        }
    }

    override fun getCurrentById(id: Long): Flow<CurrentEntity?> {
        return flow {
            emit(currents.firstOrNull { it.id == id })
        }
    }

    override suspend fun saveCurrent(currentEntity: CurrentEntity) {
        val position = currents.indexOfFirst {
            it.id == currentEntity.id
        }
        if (position >= 0) {
            currents[position] = currentEntity
        } else {
            currents.add(currentEntity)
        }
    }

    override suspend fun deleteCurrent(currentEntity: CurrentEntity) {
        currents.remove(currentEntity)
    }
}