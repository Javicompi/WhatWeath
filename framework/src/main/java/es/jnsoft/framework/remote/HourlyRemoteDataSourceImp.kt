package es.jnsoft.framework.remote

import com.haroldadmin.cnradapter.NetworkResponse
import es.jnsoft.data.model.HourlyData
import es.jnsoft.data.remote.HourlyRemoteDataSource
import es.jnsoft.domain.model.Result
import es.jnsoft.framework.mapper.mapFromResponse
import es.jnsoft.framework.remote.service.HourlyApiService
import javax.inject.Inject

class HourlyRemoteDataSourceImp @Inject constructor(
    private val apiService: HourlyApiService
) : HourlyRemoteDataSource {

    override suspend fun findHourly(lat: Double, lon: Double): Result<List<HourlyData>> {
        return when (val result = apiService.findHourlyResponseByLatLon(lat, lon)) {
            is NetworkResponse.Error -> {
                Result.Failure(result.error.message ?: "Unknown error occurred")
            }
            is NetworkResponse.Success -> {
                Result.Success(listOf<HourlyData>().mapFromResponse(result.body))
            }
            else ->
                Result.Failure("Unknown error occurred")
        }
    }
}