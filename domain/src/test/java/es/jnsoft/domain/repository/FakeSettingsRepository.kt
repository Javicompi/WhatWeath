package es.jnsoft.domain.repository

import es.jnsoft.domain.enums.Units
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSettingsRepository : SettingsRepository {

    private var units: String = Units.STANDARD.value

    private var selectedId: Long = 0L

    override fun getUnits(): Flow<Units> {
        return flow {
            when (units) {
                Units.IMPERIAL.value -> emit(Units.IMPERIAL)
                Units.METRIC.value -> emit(Units.METRIC)
                else -> emit(Units.STANDARD)
            }
        }
    }

    override suspend fun setUnits(units: Units) {
        this.units = units.value
    }

    override fun getSelectedId(): Flow<Long> {
        return flow { selectedId }
    }

    override suspend fun setSelectedId(id: Long) {
        selectedId = id
    }
}