package es.jnsoft.data.repository

import es.jnsoft.data.local.HourlyLocalDataSource
import es.jnsoft.data.mapper.HourlyDataMapper
import es.jnsoft.data.remote.HourlyRemoteDataSource
import es.jnsoft.domain.model.Hourly
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.HourlyRepository
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HourlyRepositoryImp @Inject constructor(
    private val localDataSource: HourlyLocalDataSource,
    private val remoteDataSource: HourlyRemoteDataSource
) : HourlyRepository {

    override fun getHourlies(cityId: Long): Flow<List<Hourly>> {
        return flow {
            val hourlies = localDataSource.getHourlies(cityId)
            emitAll(hourlies.map { HourlyDataMapper.mapToDomainList(it) })
            hourlies.first().let { list ->
                if (list.isNotEmpty() && shouldUpdate(list[0].deltaTime)) {
                    val newHourlies = remoteDataSource.findHourly(list[0].lat, list[0].lon)
                    if (newHourlies is Result.Success) {
                        localDataSource.saveHourlies(newHourlies.value)
                    }
                }
            }
        }
        /*return localDataSource.getHourlies(cityId).map { dataList ->
            HourlyDataMapper.mapToDomainList(dataList)
        }*/
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

    override fun shouldUpdate(deltaTime: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return currentTime - deltaTime >= TimeUnit.HOURS.toMillis(1)
    }
}