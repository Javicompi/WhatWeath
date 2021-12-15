package es.jnsoft.usecase

import es.jnsoft.usecase.repository.FakeCurrentRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCurrentsUseCaseTest {

    private lateinit var repository: FakeCurrentRepository

    private lateinit var useCase: GetCurrentsUseCase

    @Before
    fun setup() {
        repository = FakeCurrentRepository()
        useCase = GetCurrentsUseCase(repository)
    }

    @Test
    fun getCurrents_retrieveEmptyList() = runBlocking {
        val result = useCase.invoke(Unit).first()
        assert(result.isEmpty())
    }

    @Test
    fun getCurrents_retrieveList() = runBlocking {
        val list = mutableListOf(createFirstCurrent(), createSecondCurrent())
        repository.setData(list)
        val result = useCase.invoke(Unit).first()
        assert(result.isNotEmpty())
    }
}