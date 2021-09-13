package es.jnsoft.whatweath.utils

import es.jnsoft.domain.enums.Units
import es.jnsoft.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeSettingsRepository : SettingsRepository {

    private var unitsFlow: MutableStateFlow<Units> = MutableStateFlow(Units.STANDARD)

    override fun getUnits(): Flow<Units> {
        return unitsFlow
        }

    override suspend fun saveUnits(units: Units) {
        unitsFlow.value = units
    }
}