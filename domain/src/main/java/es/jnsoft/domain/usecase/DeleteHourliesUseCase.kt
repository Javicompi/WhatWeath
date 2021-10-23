package es.jnsoft.domain.usecase

import es.jnsoft.domain.repository.HourlyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteHourliesUseCase @Inject constructor(
    private val repository: HourlyRepository
) : BaseUseCase<Long, Unit> {

    override suspend fun invoke(params: Long) = withContext(Dispatchers.IO) {
        repository.deleteHourlies(params)
    }
}