package es.jnsoft.domain

import es.jnsoft.domain.model.Result
import es.jnsoft.domain.repository.FakeCurrentRepository
import es.jnsoft.domain.usecase.FindCurrentByNameUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class FindCurrentByNameUseCaseTest {

    private lateinit var repository: FakeCurrentRepository

    private lateinit var useCase: FindCurrentByNameUseCase

    @Before
    fun setup() {
        repository = FakeCurrentRepository()
        useCase = FindCurrentByNameUseCase(repository)
    }

    @Test
    fun findCurrent_resultSuccess() = runBlocking {
        val result = useCase.invoke("ok")
        assert(result is Result.Success)
        val data = (result as Result.Success).value
        assert(data.name == "Gran Alacant")
    }

    @Test
    fun findCurrent_resultFailure() = runBlocking {
        val result = useCase.invoke("")
        assert(result is Result.Failure)
    }
}