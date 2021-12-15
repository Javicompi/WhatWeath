package es.jnsoft.usecase

import es.jnsoft.domain.repository.SettingsRepository
import es.jnsoft.domain.usecase.BaseFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSelectedIdUseCase @Inject constructor(
    private val repository: SettingsRepository
) : BaseFlowUseCase<Unit, Flow<Long>> {

    override fun invoke(params: Unit): Flow<Long> {
        return repository.getSelectedId()
    }
}