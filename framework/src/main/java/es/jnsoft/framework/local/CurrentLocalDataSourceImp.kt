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

    override fun getCurrents(): Flow<List<CurrentData>> {
        return currentDao.getCurrents().map { currents ->
            currents.map { CurrentDataMapper.mapFromEntity(it) }
        }
    }

    override fun getCurrentById(id: Long): Flow<CurrentData?> {
        return currentDao.getCurrentById(id).map { current ->
            current?.let {
                CurrentDataMapper.mapFromEntity(it)
            }
        }
    }

    override suspend fun saveCurrent(current: CurrentData) {
        currentDao.saveCurrent(CurrentDataMapper.mapToEntity(current))
    }

    override suspend fun deleteCurrent(current: CurrentData) {
        currentDao.deleteCurrent(CurrentDataMapper.mapToEntity(current))
    }
}