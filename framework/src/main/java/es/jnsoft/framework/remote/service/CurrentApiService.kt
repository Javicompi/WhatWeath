package es.jnsoft.framework.remote.service

import com.haroldadmin.cnradapter.NetworkResponse
import es.jnsoft.domain.model.Units
import es.jnsoft.framework.remote.model.CurrentResponse
import es.jnsoft.framework.remote.model.ErrorResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

interface CurrentApiService {

    @GET("weather?appid=${ApiConstants.API_KEY}")
    suspend fun findCurrentResponseByLatLon(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("units") units: String? = Units.STANDARD.value,
        @Query("lang") language: String = Locale.getDefault().language
    ): NetworkResponse<CurrentResponse, ErrorResponse>

    @GET("weather?appid=${ApiConstants.API_KEY}")
    suspend fun findCurrentResponseByName(
        @Query("q") location: String,
        @Query("units") units: String? = Units.STANDARD.value,
        @Query("lang") language: String = Locale.getDefault().language
    ): NetworkResponse<CurrentResponse, ErrorResponse>

    @GET("weather?appid=${ApiConstants.API_KEY}")
    suspend fun findCurrentResponseById(
        @Query("id") id: Long,
        @Query("units") units: String? = Units.STANDARD.value,
        @Query("lang") language: String = Locale.getDefault().language
    ): NetworkResponse<CurrentResponse, ErrorResponse>
}