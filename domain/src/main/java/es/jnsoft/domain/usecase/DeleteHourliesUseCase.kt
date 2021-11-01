package es.jnsoft.domain.usecase

import es.jnsoft.domain.model.Hourly
import es.jnsoft.domain.repository.HourlyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteHourliesUseCase @Inject constructor(
    private val repository: HourlyRepository
) : BaseUseCase<List<Hourly>, Unit> {

    override suspend fun invoke(params: List<Hourly>) = withContext(Dispatchers.IO) {
        repository.deleteHourlies(params)
    }
}