package es.jnsoft.domain

import es.jnsoft.domain.repository.FakeHourlyRepository
import es.jnsoft.domain.usecase.SaveHourliesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class SaveHourliesUseCaseTest {

    private lateinit var repository: FakeHourlyRepository

    private lateinit var useCase: SaveHourliesUseCase

    @Before
    fun setup() {
        repository = FakeHourlyRepository()
        useCase = SaveHourliesUseCase(repository)
    }

    @Test
    fun emptyList_saveHourlies_listNotNull() = runBlocking {
        assert(repository.getData().isEmpty())
        val hourlies = createHourlyList(6697298).filter { it.cityId == 6697298L }
        useCase.invoke(hourlies)
        val savedHourlies = repository.getData()
        assert(savedHourlies.isNotEmpty())
        assert(savedHourlies.size == 3)
    }
}