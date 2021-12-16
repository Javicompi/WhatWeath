package es.jnsoft.framework.remote.service

import com.haroldadmin.cnradapter.NetworkResponse
import es.jnsoft.domain.model.Units
import es.jnsoft.framework.remote.model.ErrorResponse
import es.jnsoft.framework.remote.model.HourlyResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface HourlyApiService {

    @GET("onecall?appid=${ApiConstants.API_KEY}")
    suspend fun findHourlyResponseByLatLon(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String? = Units.STANDARD.value,
        @Query("lang") language: String = Locale.getDefault().language,
        @Query("exclude") exclude: String = ApiConstants.EXCLUDES_HOURLY
    ): NetworkResponse<HourlyResponse, ErrorResponse>
}