package es.jnsoft.domain.usecase

import es.jnsoft.domain.model.Hourly
import es.jnsoft.domain.model.Location
import es.jnsoft.domain.repository.HourlyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHourliesUseCase @Inject constructor(
    private val repository: HourlyRepository
) : BaseFlowUseCase<Location, Flow<List<Hourly>>?> {

    override fun invoke(params: Location): Flow<List<Hourly>> {
        return repository.getHourlies(params.lat, params.lon)
    }
}