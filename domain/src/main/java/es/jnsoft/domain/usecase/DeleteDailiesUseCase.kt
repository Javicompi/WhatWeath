package es.jnsoft.domain.usecase

import es.jnsoft.domain.model.Daily
import es.jnsoft.domain.model.Location
import es.jnsoft.domain.repository.DailyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteDailiesUseCase @Inject constructor(
    private val repository: DailyRepository
) : BaseUseCase<Location, Unit> {

    override suspend fun invoke(params: Location) = withContext(Dispatchers.IO) {
        repository.deleteDailies(params)
    }
}