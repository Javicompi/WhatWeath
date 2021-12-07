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
        val lat = 10.0
        val lon = 20.0
        val hourlies = repository.getHourlies(lat, lon).first()
        assert(hourlies.isEmpty())
    }

    @Test
    fun mapHourly_saveHourlies_retrieveHourlies() = runBlocking {
        val lat = 10.0
        val lon = 20.0
        val hourlies = createHourlyList(lat, lon)
        repository.saveHourlies(HourlyDataMapper.mapToDomainList(hourlies))
        val savedHourlies = repository.getHourlies(lat, lon).first()
        assert(savedHourlies.isNotEmpty())
        assert(savedHourlies[0].location.lat == lat && savedHourlies[0].location.lon == lon)
    }

    @Test
    fun saveHourlies_retrieveHourlies_deleteHourlies() = runBlocking {
        val firstLat = 10.0
        val firstLon = 20.0
        val firstHourlies = createHourlyList(firstLat, firstLon)
        repository.saveHourlies(HourlyDataMapper.mapToDomainList(firstHourlies))
        val secondLat = 30.0
        val secondLon = 40.0
        val secondHourlies = createHourlyList(secondLat, secondLon)
        repository.saveHourlies(HourlyDataMapper.mapToDomainList(secondHourlies))
        val hourliesSaved = repository.getHourlies(secondLat, secondLon).first()
        assert(hourliesSaved.size == 3)
        repository.deleteHourlies(HourlyDataMapper.mapToDomainList(firstHourlies))
        val emptyHourlies = repository.getHourlies(firstLat, firstLon).first()
        assert(emptyHourlies.isEmpty())
        val savedHourlies = repository.getHourlies(secondLat, secondLon).first()
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