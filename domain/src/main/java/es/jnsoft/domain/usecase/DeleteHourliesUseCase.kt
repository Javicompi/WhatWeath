package es.jnsoft.domain.usecase

import es.jnsoft.domain.model.Location
import es.jnsoft.domain.repository.HourlyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteHourliesUseCase @Inject constructor(
    private val repository: HourlyRepository
) : BaseUseCase<Location, Unit> {

    override suspend fun invoke(params: Location) = withContext(Dispatchers.IO) {
        repository.deleteHourlies(params.lat, params.lon)
    }
}