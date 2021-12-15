package es.jnsoft.usecase

import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Location
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.CurrentRepository
import es.jnsoft.domain.usecase.BaseUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FindCurrentByLatLonUseCase @Inject constructor(
    private val repository: CurrentRepository
) : BaseUseCase<Location, Result<Current>> {

    override suspend fun invoke(params: Location) = withContext(Dispatchers.IO) {
        repository.findCurrentByLatLon(params.lat, params.lon)
    }
}