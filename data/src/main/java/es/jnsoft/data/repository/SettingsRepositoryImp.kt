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

    override suspend fun setUnits(units: Units) {
        dataSource.setUnits(units)
    }

    override fun getSelectedId(): Flow<Long> {
        return dataSource.getSelectedId()
    }

    override suspend fun setSelectedId(id: Long) {
        dataSource.setSelectedId(id)
    }
}