package es.jnsoft.domain.repository

import es.jnsoft.domain.enums.Units
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {

    fun getUnits(): Flow<Units>

    suspend fun saveUnits(units: Units)
}