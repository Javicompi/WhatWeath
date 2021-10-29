package es.jnsoft.framework.datasource.remote

import es.jnsoft.domain.model.Result
import es.jnsoft.framework.remote.HourlyRemoteDataSourceImp
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HourlyRemoteDataSourceTest {

    private lateinit var apiService: FakeHourlyApiService

    private lateinit var dataSource: HourlyRemoteDataSourceImp

    @Before
    fun setup() {
        apiService = FakeHourlyApiService()
        dataSource = HourlyRemoteDataSourceImp(apiService)
    }

    @Test
    fun givenOkLocation_resultSuccess() = runBlocking {
        val result = dataSource.findHourly(10.0, 10.0)
        assert(result is Result.Success)
    }

    @Test
    fun givenWrongLocation_resultFailure() = runBlocking {
        val result = dataSource.findHourly(0.0, 0.0)
        assert(result is Result.Failure)
    }
}