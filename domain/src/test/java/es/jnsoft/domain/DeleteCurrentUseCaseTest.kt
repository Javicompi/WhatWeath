package es.jnsoft.domain

import es.jnsoft.domain.repository.FakeCurrentRepository
import es.jnsoft.domain.usecase.DeleteCurrentUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteCurrentUseCaseTest {

    private lateinit var repository: FakeCurrentRepository

    private lateinit var useCase: DeleteCurrentUseCase

    @Before
    fun setup() {
        repository = FakeCurrentRepository()
        useCase = DeleteCurrentUseCase(repository)
    }

    @Test
    fun emptyList_addCurrent_deleteCurrent_emptyList() = runBlocking {
        assert(repository.getData().isEmpty())
        val current = createFirstCurrent()
        repository.setData(mutableListOf(current))
        assert(repository.getData().isNotEmpty())
        useCase.invoke(current)
        assert(repository.getData().isEmpty())
    }
}