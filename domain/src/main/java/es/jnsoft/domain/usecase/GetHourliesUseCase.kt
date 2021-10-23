package es.jnsoft.domain.usecase

import es.jnsoft.domain.model.Hourly
import es.jnsoft.domain.repository.HourlyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetHourliesUseCase @Inject constructor(
    private val repository: HourlyRepository
) : BaseFlowUseCase<Long, Flow<List<Hourly>>?> {

    override fun invoke(params: Long): Flow<List<Hourly>> {
        return repository.getHourlies(params)
    }
}