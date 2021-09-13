package es.jnsoft.domain.usecase

import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.CurrentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FindCurrentByNameUseCase @Inject constructor(
    private val repository: CurrentRepository
) : BaseUseCase<String, Result<Current>> {

    override suspend fun invoke(params: String) = withContext(Dispatchers.IO) {
        repository.findCurrentByName(params)
    }
}