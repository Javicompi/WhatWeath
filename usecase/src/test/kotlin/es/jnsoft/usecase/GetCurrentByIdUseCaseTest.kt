package es.jnsoft.usecase

import es.jnsoft.usecase.repository.FakeCurrentRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCurrentByIdUseCaseTest {

    private lateinit var repository: FakeCurrentRepository

    private lateinit var useCase: GetCurrentByIdUseCase

    @Before
    fun setup() {
        repository = FakeCurrentRepository()
        useCase = GetCurrentByIdUseCase(repository)
    }

    @Test
    fun getCurrent_retrieveNull() = runBlocking {
        val result = useCase.invoke(1).first()
        assert(result == null)
    }

    @Test
    fun getCurrent_retrieveCurrent() = runBlocking {
        val current = createFirstCurrent()
        repository.setData(mutableListOf(current))
        val result = useCase.invoke(current.id).first()
        assert(result != null && result.id == current.id)
    }

    @Test
    fun getCurrent_retrieveWrong() = runBlocking {
        val current1 = createFirstCurrent()
        repository.setData(mutableListOf(current1))
        val current2 = createSecondCurrent()
        val result = useCase.invoke(current2.id).first()
        assert(result == null)
    }
}