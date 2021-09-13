package es.jnsoft.domain.repository

import es.jnsoft.domain.enums.Units
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSettingsRepository : SettingsRepository {
    
    private var units: String = Units.STANDARD.value

    override fun getUnits(): Flow<Units> {
        return flow {
            when(units) {
                Units.IMPERIAL.value -> emit(Units.IMPERIAL)
                Units.METRIC.value -> emit(Units.METRIC)
                else -> emit(Units.STANDARD)
            }
        }
    }

    override suspend fun saveUnits(units: Units) {
        this.units = units.value
    }
}