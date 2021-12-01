package es.jnsoft.framework.remote

import com.haroldadmin.cnradapter.NetworkResponse
import es.jnsoft.data.model.DailyData
import es.jnsoft.data.model.HourlyData
import es.jnsoft.data.remote.DailyRemoteDataSource
import es.jnsoft.domain.model.Result
import es.jnsoft.framework.mapper.mapFromResponse
import es.jnsoft.framework.remote.service.DailyApiService
import javax.inject.Inject

class DailyRemoteDataSourceImp @Inject constructor(
    private val apiService: DailyApiService
) : DailyRemoteDataSource {

    override suspend fun findDaily(lat: Double, lon: Double): Result<List<DailyData>> {
        return when (val result = apiService.findDailyResponseByLatLon(lat, lon)) {
            is NetworkResponse.Error -> {
                Result.Failure(result.error.message ?: "Unknown error occurred")
            }
            is NetworkResponse.Success -> {
                Result.Success(listOf<DailyData>().mapFromResponse(result.body))
            }
            else -> {
                Result.Failure("Unknown error occurred")
            }
        }
    }
}