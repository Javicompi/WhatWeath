package es.jnsoft.domain.usecase

import es.jnsoft.domain.model.Current
import es.jnsoft.domain.repository.CurrentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCurrentByIdUseCase @Inject constructor(
    private val repository: CurrentRepository
) : BaseUseCase<Long, Current?> {

    override suspend fun invoke(params: Long): Current? {
        return repository.getCurrentById(params)
    }
}