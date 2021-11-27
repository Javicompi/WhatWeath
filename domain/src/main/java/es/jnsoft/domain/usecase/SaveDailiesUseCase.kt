package es.jnsoft.domain.usecase

import es.jnsoft.domain.model.Daily
import es.jnsoft.domain.repository.DailyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveDailiesUseCase @Inject constructor(
    private val repository: DailyRepository
) : BaseUseCase<List<Daily>, Unit> {

    override suspend fun invoke(params: List<Daily>) = withContext(Dispatchers.IO) {
        repository.saveDailies(params)
    }
}