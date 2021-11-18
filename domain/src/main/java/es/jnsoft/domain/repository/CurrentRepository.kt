package es.jnsoft.domain.repository

import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface CurrentRepository {

    fun getCurrents(): Flow<List<Current>>

    fun getCurrentById(id: Long): Flow<Current?>

    suspend fun saveCurrent(current: Current)

    suspend fun deleteCurrent(current: Current)

    suspend fun updateCurrents()

    suspend fun findCurrentByLatLon(lat: Double, lon: Double): Result<Current>

    suspend fun findCurrentByName(name: String): Result<Current>

    suspend fun findCurrentById(id: Long): Result<Current>

    fun shouldUpdate(deltaTime: Long): Boolean
}