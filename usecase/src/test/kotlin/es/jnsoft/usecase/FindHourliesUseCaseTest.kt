package es.jnsoft.usecase

import es.jnsoft.domain.model.Location
import es.jnsoft.domain.model.Result
import es.jnsoft.usecase.repository.FakeHourlyRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FindHourliesUseCaseTest {

    private lateinit var repository: FakeHourlyRepository

    private lateinit var useCase: FindHourliesUseCase

    @Before
    fun setup() {
        repository = FakeHourlyRepository()
        useCase = FindHourliesUseCase(repository)
    }

    @Test
    fun findHourly_resultSuccess() = runBlocking {
        val successLocation = Location(2.0, 2.0)
        val result = useCase.invoke(successLocation)
        assert(result is Result.Success)
        val data = (result as Result.Success).value
        assert(data.size == 6)
    }

    @Test
    fun findHourly_resultFailure() = runBlocking {
        val failureLocation = Location(0.0, 0.0)
        val result = useCase.invoke(failureLocation)
        assert(result is Result.Failure)
    }
}