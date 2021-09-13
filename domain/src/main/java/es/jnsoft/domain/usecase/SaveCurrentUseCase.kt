package es.jnsoft.domain.usecase

import es.jnsoft.domain.model.Current
import es.jnsoft.domain.repository.CurrentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveCurrentUseCase @Inject constructor(
    private val repository: CurrentRepository
) : BaseUseCase<Current, Unit> {

    override suspend fun invoke(params: Current) = withContext(Dispatchers.IO) {
        repository.saveCurrent(params)
    }
}