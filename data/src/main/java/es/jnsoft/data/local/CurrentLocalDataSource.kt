package es.jnsoft.data.local

import es.jnsoft.data.model.CurrentData
import kotlinx.coroutines.flow.Flow

interface CurrentLocalDataSource {

    suspend fun getCurrents(): List<CurrentData>

    suspend fun getCurrentById(id: Long): CurrentData?

    suspend fun saveCurrent(current: CurrentData)

    suspend fun deleteCurrent(current: CurrentData)
}