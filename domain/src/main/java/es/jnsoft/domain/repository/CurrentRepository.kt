package es.jnsoft.domain.repository

import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Result

interface CurrentRepository {

    suspend fun getCurrents(): List<Current>

    suspend fun getCurrentById(id: Long): Current?

    suspend fun saveCurrent(current: Current)

    suspend fun deleteCurrent(current: Current)

    suspend fun findCurrentByLatLon(lat: Double, lon: Double): Result<Current>

    suspend fun findCurrentByName(name: String): Result<Current>
}