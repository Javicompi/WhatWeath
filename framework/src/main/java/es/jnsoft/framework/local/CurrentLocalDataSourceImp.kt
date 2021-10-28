package es.jnsoft.framework.local

import es.jnsoft.data.local.CurrentLocalDataSource
import es.jnsoft.data.model.CurrentData
import es.jnsoft.framework.local.dao.CurrentDao
import es.jnsoft.framework.mapper.mapToData
import es.jnsoft.framework.mapper.mapToEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrentLocalDataSourceImp @Inject constructor(
    private val currentDao: CurrentDao
) : CurrentLocalDataSource {

    override fun getCurrents(): Flow<List<CurrentData>> {
        return currentDao.getCurrents().map { currents ->
            currents.map { it.mapToData() }
        }
    }

    override fun getCurrentById(id: Long): Flow<CurrentData?> {
        return currentDao.getCurrentById(id).map { current ->
            current?.mapToData()
        }
    }

    override suspend fun saveCurrent(current: CurrentData) {
        currentDao.saveCurrent(current.mapToEntity())
    }

    override suspend fun deleteCurrent(current: CurrentData) {
        currentDao.deleteCurrent(current.mapToEntity())
    }
}