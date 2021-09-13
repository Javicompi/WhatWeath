package es.jnsoft.data.local

import es.jnsoft.data.model.CurrentData
import kotlinx.coroutines.flow.Flow

interface CurrentLocalDataSource {

    fun getCurrents(): Flow<List<CurrentData>>

     fun getCurrentById(id: Long): Flow<CurrentData?>

    suspend fun saveCurrent(current: CurrentData)

    suspend fun deleteCurrent(current: CurrentData)
}