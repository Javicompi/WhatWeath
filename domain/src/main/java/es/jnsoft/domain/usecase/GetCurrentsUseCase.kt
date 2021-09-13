package es.jnsoft.domain.usecase

import es.jnsoft.domain.model.Current
import es.jnsoft.domain.repository.CurrentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCurrentsUseCase @Inject constructor(
    private val repository: CurrentRepository
) : BaseUseCase<Unit, List<Current>> {

    override suspend fun invoke(params: Unit): List<Current> {
        return repository.getCurrents()
    }
}