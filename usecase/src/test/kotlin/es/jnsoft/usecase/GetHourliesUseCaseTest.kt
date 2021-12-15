package es.jnsoft.usecase

import es.jnsoft.domain.model.Location
import es.jnsoft.usecase.repository.FakeHourlyRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetHourliesUseCaseTest {

    private lateinit var repository: FakeHourlyRepository

    private lateinit var useCase: GetHourliesUseCase

    @Before
    fun setup() {
        repository = FakeHourlyRepository()
        useCase = GetHourliesUseCase(repository)
    }

    @Test
    fun getHourlies_retrieveEmptyList() = runBlocking {
        val result = useCase.invoke(Location(0.0, 0.0)).first()
        assert(result.isEmpty())
    }

    @Test
    fun getHourlies_retrieveList() = runBlocking {
        val lat = 35.379
        val lon = -0.157
        val list = createHourlyList(lat, lon)
        repository.setData(list.toMutableList())
        val result = useCase.invoke(Location(lat, lon)).first()
        assert(result.isNotEmpty())
        assert(result.size == 3)
    }

    @Test
    fun getHourlies_retrieveList_updateValues() = runBlocking {
        val lat = 35.379
        val lon = -0.157
        val list = createHourlyList(lat, lon)
        repository.setData(list.toMutableList())
        val result = useCase.invoke(Location(lat, lon)).take(2).toList()
        assert(result[0].size == 3)
        assert(result[1].size == 3)
        assert(result[0][0].location.lat == lat)
        assert(result[1][0].location.lat == lat)
        assert(result[1][0].deltaTime > result[0][0].deltaTime)
    }
}