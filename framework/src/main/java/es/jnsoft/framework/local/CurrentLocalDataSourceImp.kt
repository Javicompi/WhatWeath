package es.jnsoft.framework.local

import es.jnsoft.data.local.CurrentLocalDataSource
import es.jnsoft.data.model.CurrentData
import es.jnsoft.framework.local.dao.CurrentDao
import es.jnsoft.framework.mapper.CurrentDataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrentLocalDataSourceImp @Inject constructor(
    private val currentDao: CurrentDao
) : CurrentLocalDataSource {

    override suspend fun getCurrents(): List<CurrentData> {
        /*return currentDao.getCurrents().map { currents ->
            currents.map { CurrentDataMapper.mapFromEntity(it) }
        }*/
        return currentDao.getCurrents().map { currentEntity ->
            CurrentDataMapper.mapFromEntity(currentEntity)
        }
    }

    override suspend fun getCurrentById(id: Long): CurrentData? {
        /*return currentDao.getCurrentById(id).map { current ->
            current?.let {
                CurrentDataMapper.mapFromEntity(it)
            }
        }*/
        val data = currentDao.getCurrentById(id)
        return data?.let { currentEntity ->
            CurrentDataMapper.mapFromEntity(currentEntity)
        }
    }

    override suspend fun saveCurrent(current: CurrentData) {
        currentDao.saveCurrent(CurrentDataMapper.mapToEntity(current))
    }

    override suspend fun deleteCurrent(current: CurrentData) {
        currentDao.deleteCurrent(CurrentDataMapper.mapToEntity(current))
    }
}