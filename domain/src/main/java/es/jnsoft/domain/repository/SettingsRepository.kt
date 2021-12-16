package es.jnsoft.domain.repository

import es.jnsoft.domain.model.Units
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getUnits(): Flow<Units>

    suspend fun setUnits(units: Units)

    fun getSelectedId(): Flow<Long>

    suspend fun setSelectedId(id: Long)
}