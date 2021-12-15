package es.jnsoft.usecase

import es.jnsoft.domain.model.Current
import es.jnsoft.domain.repository.CurrentRepository
import es.jnsoft.domain.usecase.BaseFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentsUseCase @Inject constructor(
    private val repository: CurrentRepository
) : BaseFlowUseCase<Unit, Flow<List<Current>>> {

    override fun invoke(params: Unit): Flow<List<Current>> {
        return repository.getCurrents()
    }
}