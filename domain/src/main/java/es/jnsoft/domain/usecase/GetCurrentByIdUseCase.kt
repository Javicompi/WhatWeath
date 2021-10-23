package es.jnsoft.domain.usecase

import es.jnsoft.domain.model.Current
import es.jnsoft.domain.repository.CurrentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentByIdUseCase @Inject constructor(
    private val repository: CurrentRepository
) : BaseFlowUseCase<Long, Flow<Current?>> {

    override fun invoke(params: Long): Flow<Current?> {
        return repository.getCurrentById(params)
    }
}