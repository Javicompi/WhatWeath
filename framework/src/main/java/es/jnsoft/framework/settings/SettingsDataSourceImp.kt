package es.jnsoft.framework.settings

import es.jnsoft.data.settings.SettingsDataSource
import es.jnsoft.domain.enums.Units
import es.jnsoft.framework.settings.datastore.SettingsDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsDataSourceImp @Inject constructor(
    private val settings: SettingsDataStore
) : SettingsDataSource {

    override fun getUnits(): Flow<Units> {
        return settings.unitsPreferences.map { value ->
            when (value) {
                Units.METRIC.value -> Units.METRIC
                Units.IMPERIAL.value -> Units.IMPERIAL
                else -> Units.STANDARD
            }
        }
        /*return flow {
            settings.unitsPreferences.map { value ->
                emit(
                    when (value) {
                        Units.METRIC.value -> Units.METRIC
                        Units.IMPERIAL.value -> Units.IMPERIAL
                        else -> Units.STANDARD
                    }
                )
            }
        }*/
        //return settings.unitsPreferences
    }

    override suspend fun setUnits(units: Units) {
        settings.setUnits(units)
    }
}