package es.jnsoft.framework.remote

import com.haroldadmin.cnradapter.NetworkResponse
import es.jnsoft.data.model.CurrentData
import es.jnsoft.data.remote.CurrentRemoteDataSource
import es.jnsoft.domain.model.Result
import es.jnsoft.framework.mapper.CurrentDataMapper
import es.jnsoft.framework.remote.service.CurrentApiService
import javax.inject.Inject

class CurrentRemoteDataSourceImp @Inject constructor(
    private val apiApiService: CurrentApiService
) : CurrentRemoteDataSource {

    override suspend fun findCurrentByLatLon(
        lat: Double,
        lon: Double
    ): Result<CurrentData> {
        return when (val result = apiApiService.findCurrentResponseByLatLon(lat, lon)) {
            is NetworkResponse.Error -> {
                Result.Failure(result.error.message ?: "Unknown error occurred")
            }
            is NetworkResponse.Success -> {
                val data = result.body
                Result.Success(CurrentDataMapper.mapFromResponse(data))
            }
            else -> {
                Result.Failure("Unknown error occurred")
            }
        }
    }

    override suspend fun findCurrentByName(name: String): Result<CurrentData> {
        return when (val result = apiApiService.findCurrentResponseByName(name)) {
            is NetworkResponse.Error -> {
                Result.Failure(result.error.message ?: "Unknown error occurred")
            }
            is NetworkResponse.Success -> {
                val data = result.body
                Result.Success(CurrentDataMapper.mapFromResponse(data))
            }
            else -> {
                Result.Failure("Unknown error occurred")
            }
        }
    }
}