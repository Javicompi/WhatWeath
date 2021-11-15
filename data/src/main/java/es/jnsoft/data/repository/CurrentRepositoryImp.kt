package es.jnsoft.data.repository

import es.jnsoft.data.local.CurrentLocalDataSource
import es.jnsoft.data.mapper.CurrentDataMapper
import es.jnsoft.data.remote.CurrentRemoteDataSource
import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.CurrentRepository
import kotlinx.coroutines.flow.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrentRepositoryImp @Inject constructor(
    private val localDataSource: CurrentLocalDataSource,
    private val remoteDataSource: CurrentRemoteDataSource,
) : CurrentRepository {

    override fun getCurrents(): Flow<List<Current>> {
        return flow {
            val currents = localDataSource.getCurrents()
            currents.first().let { list ->
                list.map { current ->
                    if (shouldUpdate(current.deltaTime)) {
                        val newData = remoteDataSource.findCurrentByLatLon(current.lat, current.lon)
                        if (newData is Result.Success) {
                            localDataSource.saveCurrent(newData.value)
                        }
                    }
                }
            }
            emitAll(currents.map { CurrentDataMapper.mapToDomainList(it) })
        }
        /*return localDataSource.getCurrents().map { list ->
            CurrentDataMapper.mapToDomainList(list)
        }*/
    }

    override fun getCurrentById(id: Long): Flow<Current?> {
        return flow {
            val current = localDataSource.getCurrentById(id)
            current.first()?.let { value ->
                if (shouldUpdate(value.deltaTime)) {
                    val newData = remoteDataSource.findCurrentByLatLon(value.lat, value.lon)
                    if (newData is Result.Success) {
                        localDataSource.saveCurrent(newData.value)
                    }
                }
            }
            emitAll(current.map { value ->
                value?.let { CurrentDataMapper.mapToDomain(it) }
            })
        }
        /*val data = localDataSource.getCurrentById(id)
        return data.map {
            if (it != null) CurrentDataMapper.mapToDomain(it) else null
        }*/
    }

    override suspend fun saveCurrent(current: Current) {
        localDataSource.saveCurrent(CurrentDataMapper.mapFromDomain(current))
    }

    override suspend fun deleteCurrent(current: Current) {
        localDataSource.deleteCurrent(CurrentDataMapper.mapFromDomain(current))
    }

    override suspend fun findCurrentByLatLon(lat: Double, lon: Double): Result<Current> {
        return when (val result = remoteDataSource.findCurrentByLatLon(lat, lon)) {
            is Result.Success -> Result.Success(CurrentDataMapper.mapToDomain(result.value))
            is Result.Failure -> result
            is Result.Loading -> result
        }
    }

    override suspend fun findCurrentByName(name: String): Result<Current> {
        return when (val result = remoteDataSource.findCurrentByName(name)) {
            is Result.Success -> Result.Success(CurrentDataMapper.mapToDomain(result.value))
            is Result.Failure -> result
            is Result.Loading -> result
        }
    }

    override fun shouldUpdate(deltaTime: Long): Boolean {
        val currentTime = System.currentTimeMillis()
        return currentTime - deltaTime >= TimeUnit.HOURS.toMillis(1)
    }
}