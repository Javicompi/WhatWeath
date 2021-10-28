package es.jnsoft.domain

import es.jnsoft.domain.repository.FakeHourlyRepository
import es.jnsoft.domain.usecase.GetHourliesUseCase
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
        val result = useCase.invoke(6697298).first()
        assert(result.isEmpty())
    }

    @Test
    fun getHourlies_retrieveList() = runBlocking {
        val list = createHourlyList(6697298)
        repository.setData(list.toMutableList())
        val result = useCase.invoke(6697298).first()
        assert(result.isNotEmpty())
        assert(result.size == 3)
    }

    @Test
    fun getHourlies_retrieveList_updateValues() = runBlocking {
        val cityId = 6697298L
        val list = createHourlyList(cityId)
        repository.setData(list.toMutableList())
        val result = useCase.invoke(cityId).take(2).toList()
        assert(result[0].size == 3)
        assert(result[1].size == 3)
        assert(result[0][0].cityId == cityId)
        assert(result[1][0].cityId == cityId)
        assert(result[1][0].deltaTime > result[0][0].deltaTime)
    }
}