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

    override fun getHourlies(lat: Double, lon: Double): Flow<List<Hourly>> {
        return flow {
            val hourlies = localDataSource.getHourlies(lat, lon)
            if (hourlies.first().isNotEmpty() && shouldUpdate(hourlies.first()[0].deltaTime)) {
                updateHourlies(lat, lon)
            }
            emitAll(hourlies.map { HourlyDataMapper.mapToDomainList(it) })
        }
    }

    override suspend fun saveHourlies(hourlies: List<Hourly>) {
        localDataSource.saveHourlies(HourlyDataMapper.mapFromDomainList(hourlies))
    }

    override suspend fun deleteHourlies(hourlies: List<Hourly>) {
        localDataSource.deleteHourlies(HourlyDataMapper.mapFromDomainList(hourlies))
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

    override suspend fun updateHourlies(lat: Double, lon: Double) {
        val newHourlies = remoteDataSource.findHourly(lat, lon)
        if (newHourlies is Result.Success) {
            localDataSource.deleteHourlies(newHourlies.value)
            localDataSource.saveHourlies(newHourlies.value)
        }
    }

    override fun shouldUpdate(deltaTime: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return currentTime - deltaTime >= TimeUnit.HOURS.toMillis(1)
    }
}