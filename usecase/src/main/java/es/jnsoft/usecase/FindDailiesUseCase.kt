package es.jnsoft.usecase

import es.jnsoft.domain.model.Daily
import es.jnsoft.domain.model.Location
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.DailyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FindDailiesUseCase @Inject constructor(
    private val repository: DailyRepository
) : BaseUseCase<Location, Result<List<Daily>>> {

    override suspend fun invoke(params: Location) = withContext(Dispatchers.IO) {
        repository.findDailies(params.lat, params.lon)
    }
}