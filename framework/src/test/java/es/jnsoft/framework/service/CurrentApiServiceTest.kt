package es.jnsoft.framework.service

import com.haroldadmin.cnradapter.NetworkResponse
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import es.jnsoft.framework.remote.service.ApiConstants
import es.jnsoft.framework.remote.service.CurrentApiService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CurrentApiServiceTest {

    private lateinit var apiService: CurrentApiService

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
            .create(CurrentApiService::class.java)
    }

    @Test
    fun givenOkLocation_networkResponse200() = runBlocking {
        val lat = 38.2324
        val lon = -0.547
        val result = apiService.findCurrentResponseByLatLon(lat, lon)
        assert(result is NetworkResponse.Success)
        assert((result as NetworkResponse.Success).code == 200)
    }

    @Test
    fun givenWrongLocation_networkResponse400() = runBlocking {
        val latLon = -100.0
        val result = apiService.findCurrentResponseByLatLon(latLon, latLon)
        assert(result is NetworkResponse.ServerError)
        assert((result as NetworkResponse.ServerError).code == 400)
    }

    @Test
    fun givenOkName_networkResponse200() = runBlocking {
        val name = "gran alacant"
        val result = apiService.findCurrentResponseByName(name)
        assert(result is NetworkResponse.Success)
        assert((result as NetworkResponse.Success).code == 200)
    }

    @Test
    fun givenEmptyName_networkResponse400() = runBlocking {
        val name = ""
        val result = apiService.findCurrentResponseByName(name)
        assert(result is NetworkResponse.ServerError)
        assert((result as NetworkResponse.ServerError).code == 400)
    }

    @Test
    fun givenWrongName_networkResponse404() = runBlocking {
        val name = "lyx"
        val result = apiService.findCurrentResponseByName(name)
        assert(result is NetworkResponse.ServerError)
        assert((result as NetworkResponse.ServerError).code == 404)
    }
}