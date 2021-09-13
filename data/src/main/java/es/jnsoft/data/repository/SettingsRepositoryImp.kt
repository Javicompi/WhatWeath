package es.jnsoft.data.repository

import es.jnsoft.data.settings.SettingsDataSource
import es.jnsoft.domain.enums.Units
import es.jnsoft.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SettingsRepositoryImp @Inject constructor(
    private val dataSource: SettingsDataSource
) : SettingsRepository {

    override fun getUnits(): Flow<Units> {
        return dataSource.getUnits()
    }

    override suspend fun saveUnits(units: Units) {
        dataSource.setUnits(units)
    }
}