package es.jnsoft.data

import es.jnsoft.data.mapper.HourlyDataMapper
import es.jnsoft.data.repository.HourlyRepositoryImp
import es.jnsoft.data.source.FakeHourlyLocalDataSource
import es.jnsoft.data.source.FakeHourlyRemoteDataSource
import es.jnsoft.domain.model.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class HourlyRepositoryTest {

    private lateinit var localDataSource: FakeHourlyLocalDataSource
    private lateinit var remoteDataSource: FakeHourlyRemoteDataSource
    private lateinit var repository: HourlyRepositoryImp

    @Before
    fun createRepository() {
        localDataSource = FakeHourlyLocalDataSource()
        remoteDataSource = FakeHourlyRemoteDataSource()
        repository = HourlyRepositoryImp(localDataSource, remoteDataSource)
    }

    @After
    fun clearData() {
        localDataSource.clearData()
    }

    @Test
    fun getHourlies_retrieveEmptyList() = runBlocking {
        val hourlies = repository.getHourlies(6697298).first()
        assert(hourlies.isEmpty())
    }

    @Test
    fun mapHourly_saveHourlies_retrieveHourlies() = runBlocking {
        val cityId = 6697298L
        val hourlies = createHourlyList(cityId)
        repository.saveHourlies(HourlyDataMapper.mapToDomainList(hourlies))
        val savedHourlies = repository.getHourlies(cityId).first()
        assert(savedHourlies.isNotEmpty())
        assert(savedHourlies[0].cityId == cityId)
    }

    @Test
    fun saveHourlies_retrieveHourlies_deleteHourlies() = runBlocking {
        val firstCityId = 6697298L
        val firstHourlies = createHourlyList(firstCityId)
        repository.saveHourlies(HourlyDataMapper.mapToDomainList(firstHourlies))
        val secondCityId = 7729981L
        val secondHourlies = createHourlyList(secondCityId)
        repository.saveHourlies(HourlyDataMapper.mapToDomainList(secondHourlies))
        val hourliesSaved = repository.getHourlies(firstCityId).first()
        assert(hourliesSaved.size == 3)
        repository.deleteHourlies(firstCityId)
        val emptyHourlies = repository.getHourlies(firstCityId).first()
        assert(emptyHourlies.isEmpty())
        val savedHourlies = repository.getHourlies(secondCityId).first()
        assert(savedHourlies.size == 3)
    }

    @Test
    fun findHourlies_returnSuccess() = runBlocking {
        val result = repository.findHourlies(2.0, 2.0)
        assert(result is Result.Success)
    }

    @Test
    fun findHourlies_returnFailure() = runBlocking {
        val result = repository.findHourlies(0.0, 0.0)
        assert(result is Result.Failure)
    }
}