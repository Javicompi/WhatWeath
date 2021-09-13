package es.jnsoft.domain

import es.jnsoft.domain.repository.FakeCurrentRepository
import es.jnsoft.domain.usecase.SaveCurrentUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveCurrentUseCaseTest {

    private lateinit var repository: FakeCurrentRepository

    private lateinit var useCase: SaveCurrentUseCase

    @Before
    fun setup() {
        repository = FakeCurrentRepository()
        useCase = SaveCurrentUseCase(repository)
    }

    @Test
    fun emptyList_saveCurrent_listNotNull() = runBlocking {
        assert(repository.getData().isEmpty())
        val current = createFirstCurrent()
        useCase.invoke(current)
        val list = repository.getData()
        assert(list.isNotEmpty())
        assert((list.find { it.id == current.id }) != null)
    }
}