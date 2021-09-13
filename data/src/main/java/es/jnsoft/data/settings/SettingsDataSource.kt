package es.jnsoft.data.settings

import es.jnsoft.domain.enums.Units
import kotlinx.coroutines.flow.Flow

interface SettingsDataSource {

    fun getUnits(): Flow<Units>

    suspend fun setUnits(units: Units)
}