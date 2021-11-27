package es.jnsoft.domain.usecase

import es.jnsoft.domain.model.Daily
import es.jnsoft.domain.model.Location
import es.jnsoft.domain.repository.DailyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDailiesUseCase @Inject constructor(
    private val repository: DailyRepository
) : BaseFlowUseCase<Location, Flow<List<Daily>>?> {

    override fun invoke(params: Location): Flow<List<Daily>> {
        return repository.getDailies(params.lat, params.lon)
    }
}