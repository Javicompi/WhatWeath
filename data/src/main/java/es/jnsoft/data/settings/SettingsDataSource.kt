package es.jnsoft.data.settings

import es.jnsoft.domain.model.Units
import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {

    fun getUnits(): Flow<Units>

    suspend fun setUnits(units: Units)

    fun getSelectedId(): Flow<Long>

    suspend fun setSelectedId(id: Long)
}