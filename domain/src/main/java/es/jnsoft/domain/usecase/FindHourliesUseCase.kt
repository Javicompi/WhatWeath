package es.jnsoft.domain.usecase

import es.jnsoft.domain.model.Hourly
import es.jnsoft.domain.model.Location
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.HourlyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FindHourliesUseCase @Inject constructor(
    private val repository: HourlyRepository
) : BaseUseCase<Location, Result<List<Hourly>>> {

    override suspend fun invoke(params: Location) = withContext(Dispatchers.IO) {
        repository.findHourlies(params.lat, params.lon)
    }
}