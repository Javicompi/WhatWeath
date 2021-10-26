package es.jnsoft.domain

import es.jnsoft.domain.repository.FakeHourlyRepository
import es.jnsoft.domain.usecase.GetHourliesUseCase
import kotlinx.coroutines.flow.first
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
}