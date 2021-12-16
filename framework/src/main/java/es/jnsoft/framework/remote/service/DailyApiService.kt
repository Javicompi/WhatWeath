package es.jnsoft.framework.remote.service

import com.haroldadmin.cnradapter.NetworkResponse
import es.jnsoft.domain.model.Units
import es.jnsoft.framework.remote.model.DailyResponse
import es.jnsoft.framework.remote.model.ErrorResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface DailyApiService {

    @GET("onecall?appid=${ApiConstants.API_KEY}")
    suspend fun findDailyResponseByLatLon(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String? = Units.STANDARD.value,
        @Query("lang") language: String = Locale.getDefault().language,
        @Query("exclude") exclude: String = ApiConstants.EXCLUDES_DAILY
    ): NetworkResponse<DailyResponse, ErrorResponse>
}