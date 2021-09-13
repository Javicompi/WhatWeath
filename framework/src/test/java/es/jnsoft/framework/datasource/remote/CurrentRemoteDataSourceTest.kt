package es.jnsoft.framework.datasource.remote

import es.jnsoft.domain.model.Result
import es.jnsoft.framework.remote.CurrentRemoteDataSourceImp
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CurrentRemoteDataSourceTest {

    private lateinit var apiService: FakeCurrentApiService

    private lateinit var dataSource: CurrentRemoteDataSourceImp

    @Before
    fun setup() {
        apiService = FakeCurrentApiService()
        dataSource = CurrentRemoteDataSourceImp(apiService)
    }

    @Test
    fun givenOkLocation_resultSuccess() = runBlocking {
        val lat = 100.0
        val lon = 100.0
        val result = dataSource.findCurrentByLatLon(lat, lon)
        assert(result is Result.Success)
    }

    @Test
    fun givenWrongLocation_resultFailure() = runBlocking {
        val latLon = -100.0
        val result = dataSource.findCurrentByLatLon(latLon, latLon)
        assert(result is Result.Failure)
    }

    @Test
    fun givenOkName_resultSuccess() = runBlocking {
        val name = "alicante"
        val result = dataSource.findCurrentByName(name)
        assert(result is Result.Success)
    }

    @Test
    fun givenEmptyName_resultFailure() = runBlocking {
        val name = ""
        val result = dataSource.findCurrentByName(name)
        assert(result is Result.Failure)
        assert((result as Result.Failure).message.contains("server error"))
    }

    @Test
    fun givenWrongName_resultFailure() = runBlocking {
        val name = "lyx"
        val result = dataSource.findCurrentByName(name)
        assert(result is Result.Failure)
        assert((result as Result.Failure).message.contains("server error"))
    }
}