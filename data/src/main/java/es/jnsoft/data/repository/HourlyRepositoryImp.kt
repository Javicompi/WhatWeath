package es.jnsoft.data.repository

import es.jnsoft.data.local.HourlyLocalDataSource
import es.jnsoft.data.mapper.HourlyDataMapper
import es.jnsoft.data.remote.HourlyRemoteDataSource
import es.jnsoft.domain.model.Hourly
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.HourlyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HourlyRepositoryImp @Inject constructor(
    private val localDataSource: HourlyLocalDataSource,
    private val remoteDataSource: HourlyRemoteDataSource
) : HourlyRepository {

    override fun getHourlies(cityId: Long): Flow<List<Hourly>> {
        return localDataSource.getHourlies(cityId).map { dataList ->
            HourlyDataMapper.mapToDomainList(dataList)
        }
    }

    override suspend fun saveHourlies(hourlies: List<Hourly>) {
        localDataSource.saveHourlies(HourlyDataMapper.mapFromDomainList(hourlies))
    }

    override suspend fun deleteHourlies(cityId: Long) {
        localDataSource.deleteHourlies(cityId)
    }

    override suspend fun findHourlies(lat: Double, lon: Double): Result<List<Hourly>> {
        return when (val result = remoteDataSource.findHourly(lat, lon)) {
            is Result.Success -> {
                Result.Success(HourlyDataMapper.mapToDomainList(result.value))
            }
            is Result.Failure -> result
            is Result.Loading -> result
        }
    }
}