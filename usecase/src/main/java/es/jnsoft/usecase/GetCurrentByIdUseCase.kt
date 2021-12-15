package es.jnsoft.usecase

import es.jnsoft.domain.model.Current
import es.jnsoft.domain.repository.CurrentRepository
import es.jnsoft.domain.usecase.BaseFlowUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentByIdUseCase @Inject constructor(
    private val repository: CurrentRepository
) : BaseFlowUseCase<Long, Flow<Current?>> {

    override fun invoke(params: Long): Flow<Current?> {
        return repository.getCurrentById(params)
    }
}