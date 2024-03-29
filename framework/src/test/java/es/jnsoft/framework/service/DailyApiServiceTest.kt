package es.jnsoft.framework.service

import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import es.jnsoft.framework.remote.service.ApiConstants
import es.jnsoft.framework.remote.service.DailyApiService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class DailyApiServiceTest {

    private lateinit var apiService: DailyApiService

    private lateinit var moshi: Moshi

    @Before
    fun setup() {
        moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        apiService = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .build()
            .create(DailyApiService::class.java)
    }

    @Test
    fun givenOkLocation_networkResponse200() = runBlocking {
        val lat = 38.2324
        val lon = -0.547
        val result = apiService.findDailyResponseByLatLon(lat, lon)
        assert(result is NetworkResponse.Success)
        assert((result as NetworkResponse.Success).code == 200)
    }

    @Test
    fun givenWrongLocation_networkResponse400() = runBlocking {
        val latLon = 100.0
        val result = apiService.findDailyResponseByLatLon(latLon, latLon)
        assert(result is NetworkResponse.ServerError)
        assert((result as NetworkResponse.ServerError).code == 400)
    }
}