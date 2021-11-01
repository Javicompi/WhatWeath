package es.jnsoft.domain

import es.jnsoft.domain.model.Hourly
import es.jnsoft.domain.repository.FakeHourlyRepository
import es.jnsoft.domain.usecase.DeleteHourliesUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteHourliesUseCaseTest {

    private lateinit var repository: FakeHourlyRepository

    private lateinit var useCase: DeleteHourliesUseCase

    @Before
    fun setup() {
        repository = FakeHourlyRepository()
        useCase = DeleteHourliesUseCase(repository)
    }

    @Test
    fun emptyList_addHourlies_deleteHourlies_emptyList() = runBlocking {
        assert(repository.getData().isEmpty())
        val lat = 38.2246
        val lon = -0.5193
        val hourlies = createHourlyList(lat, lon)
        repository.setData(hourlies as MutableList<Hourly>)
        assert(repository.getData().size == 6)
        useCase.invoke(hourlies.subList(0, 3))
        assert(repository.getData().size == 3)
    }
}