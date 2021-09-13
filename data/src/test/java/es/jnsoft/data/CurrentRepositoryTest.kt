package es.jnsoft.data

import es.jnsoft.data.mapper.CurrentDataMapper
import es.jnsoft.data.repository.CurrentRepositoryImp
import es.jnsoft.data.source.FakeCurrentLocalDataSource
import es.jnsoft.data.source.FakeCurrentRemoteDataSource
import es.jnsoft.domain.model.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test

class CurrentRepositoryTest {

    private lateinit var localDataSource: FakeCurrentLocalDataSource
    private lateinit var remoteDataSource: FakeCurrentRemoteDataSource
    private lateinit var currentRepository: CurrentRepositoryImp

    @Before
    fun createRepository() {
        localDataSource = FakeCurrentLocalDataSource()
        remoteDataSource = FakeCurrentRemoteDataSource()
        currentRepository = CurrentRepositoryImp(localDataSource, remoteDataSource)
    }

    @After
    fun clearData() {
        localDataSource.clearData()
    }

    @Test
    fun getCurrents_retrieveEmptyList() = runBlocking {
        val currents = currentRepository.getCurrents().first()
        assert(currents.isEmpty())
    }

    @Test
    fun getCurrent_retrieveNothing() = runBlocking {
        val current = currentRepository.getCurrentById(0).first()
        assert(current == null)
    }

    @Test
    fun mapCurrent_saveCurrent_retrieveCurrent() = runBlocking {
        val current = createFirstCurrent()
        currentRepository.saveCurrent(CurrentDataMapper.mapToDomain(current))
        val newCurrent = currentRepository.getCurrentById(current.id).first()
        assert(newCurrent != null)
        assert(current.name == newCurrent!!.name)
    }

    @Test
    fun saveCurrents_retrieveCurrents_deleteCurrents() = runBlocking {
        val current1 = createFirstCurrent()
        currentRepository.saveCurrent(CurrentDataMapper.mapToDomain(current1))
        val current2 = createSecondCurrent()
        currentRepository.saveCurrent(CurrentDataMapper.mapToDomain(current2))
        val currents = currentRepository.getCurrents().first()
        assert(currents.size == 2)
        currentRepository.deleteCurrent(CurrentDataMapper.mapToDomain(current1))
        currentRepository.deleteCurrent(CurrentDataMapper.mapToDomain(current2))
        val emptyCurrents = currentRepository.getCurrents().first()
        assert(emptyCurrents.isEmpty())
    }

    @Test
    fun findCurrentByLatLon_success() = runBlocking {
        val result = currentRepository.findCurrentByLatLon(1.0, 1.0)
        assert(result is Result.Success)
    }

    @Test
    fun findCurrentByLatLon_failure() = runBlocking {
        val result = currentRepository.findCurrentByLatLon(0.0, 0.0)
        assert(result is Result.Failure)
    }

    @Test
    fun findCurrentByName_success() = runBlocking {
        val result = currentRepository.findCurrentByName("whatever")
        assert(result is Result.Success)
    }

    @Test
    fun findCurrentByName_failure() = runBlocking {
        val result = currentRepository.findCurrentByName("")
        assert(result is Result.Failure)
    }

    @Test
    fun findCurrent_saveCurrent_retrieveCurrent_deleteCurrent() = runBlocking {
        val result = currentRepository.findCurrentByName("whatever")
        assert(result is Result.Success)
        val domainCurrent = (result as Result.Success).value
        currentRepository.saveCurrent(domainCurrent)
        val retrievedCurrent = currentRepository.getCurrentById(domainCurrent.id).first()
        assert(retrievedCurrent != null && retrievedCurrent.id == domainCurrent.id)
        currentRepository.deleteCurrent(retrievedCurrent!!)
        val emptyCurrents = currentRepository.getCurrents().first()
        assert(emptyCurrents.isEmpty())
    }
}