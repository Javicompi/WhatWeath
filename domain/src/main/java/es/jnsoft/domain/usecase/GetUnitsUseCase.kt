package es.jnsoft.domain.usecase

import es.jnsoft.domain.enums.Units
import es.jnsoft.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUnitsUseCase @Inject constructor(
    private val repository: SettingsRepository
) : BaseFlowUseCase<Unit, Flow<Units>> {

    override fun invoke(params: Unit): Flow<Units> {
        return repository.getUnits()
    }
}