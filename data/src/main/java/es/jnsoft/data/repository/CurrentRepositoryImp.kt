package es.jnsoft.data.repository

import es.jnsoft.data.local.CurrentLocalDataSource
import es.jnsoft.data.mapper.CurrentDataMapper
import es.jnsoft.data.remote.CurrentRemoteDataSource
import es.jnsoft.domain.model.Current
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.CurrentRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CurrentRepositoryImp @Inject constructor(
    private val localDataSource: CurrentLocalDataSource,
    private val remoteDataSource: CurrentRemoteDataSource,
) : CurrentRepository {

    override fun getCurrents(): Flow<List<Current>> {
        return localDataSource.getCurrents().map { list ->
            CurrentDataMapper.mapToDomainList(list)
        }
    }

    override fun getCurrentById(id: Long): Flow<Current?> {
        val data = localDataSource.getCurrentById(id)
        return data.map {
            if (it != null) CurrentDataMapper.mapToDomain(it) else null
        }
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
}