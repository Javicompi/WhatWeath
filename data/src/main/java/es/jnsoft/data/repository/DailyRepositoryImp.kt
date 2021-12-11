package es.jnsoft.data.repository

import es.jnsoft.data.local.DailyLocalDataSource
import es.jnsoft.data.mapper.DailyDataMapper
import es.jnsoft.data.remote.DailyRemoteDataSource
import es.jnsoft.domain.model.Daily
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.DailyRepository
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DailyRepositoryImp @Inject constructor(
    private val localDataSource: DailyLocalDataSource,
    private val remoteDataSource: DailyRemoteDataSource
) : DailyRepository {

    override fun getDailies(lat: Double, lon: Double): Flow<List<Daily>> {
        return flow {
            val dailies = localDataSource.getDailies(lat, lon)
            if (dailies.first().isNotEmpty() and shouldUpdate(dailies.first()[0].deltaTime)) {
                updateDailies(lat, lon)
            }
            emitAll(dailies.map { DailyDataMapper.mapToDomainList(it) })
        }
    }

    override suspend fun saveDailies(dailies: List<Daily>) {
        localDataSource.saveDailies(DailyDataMapper.mapFromDomainList(dailies))
    }

    override suspend fun deleteDailies(lat: Double, lon: Double) {
        localDataSource.deleteDailies(lat, lon)
    }

    override suspend fun findDailies(lat: Double, lon: Double): Result<List<Daily>> {
        return when (val result = remoteDataSource.findDaily(lat, lon)) {
            is Result.Success -> {
                Result.Success(DailyDataMapper.mapToDomainList(result.value))
            }
            is Result.Failure -> result
            is Result.Loading -> result
        }
    }

    override suspend fun updateDailies(lat: Double, lon: Double) {
        val newDailies = remoteDataSource.findDaily(lat, lon)
        if (newDailies is Result.Success) {
            localDataSource.saveDailies(newDailies.value)
        }
    }

    override fun shouldUpdate(deltaTime: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return currentTime - deltaTime >= TimeUnit.HOURS.toMillis(1)
    }
}