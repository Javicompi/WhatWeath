package es.jnsoft.domain

import es.jnsoft.domain.model.Location
import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.FakeCurrentRepository
import es.jnsoft.domain.usecase.FindCurrentByLatLonUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FindCurrentByLatLonUseCaseTest {

    private lateinit var repository: FakeCurrentRepository

    private lateinit var useCase: FindCurrentByLatLonUseCase

    @Before
    fun setup() {
        repository = FakeCurrentRepository()
        useCase = FindCurrentByLatLonUseCase(repository)
    }

    @Test
    fun findCurrent_resultSuccess() = runBlocking {
        val successLocation = Location(1.0, 1.0)
        val result = useCase.invoke(successLocation)
        assert(result is Result.Success)
        val data = (result as Result.Success).value
        assert(data.name == "Gran Alacant")
    }

    @Test
    fun findCurrent_resultFailure() = runBlocking {
        val failureLocation = Location(0.0, 0.0)
        val result = useCase.invoke(failureLocation)
        assert(result is Result.Failure)
    }
}