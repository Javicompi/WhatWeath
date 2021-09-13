package es.jnsoft.framework.datasource.local

import es.jnsoft.framework.local.CurrentLocalDataSourceImp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CurrentLocalDataSourceTest {

    private lateinit var currentDao: FakeCurrentDao

    private lateinit var dataSource: CurrentLocalDataSourceImp

    @Before
    fun setup() {
        currentDao = FakeCurrentDao()
        dataSource = CurrentLocalDataSourceImp(currentDao)
    }

    @Test
    fun saveCurrent_retrieveCurrent() = runBlocking {
        val current = createFirstCurrent()
        currentDao.saveCurrent(current)
        val retrieved = currentDao.getCurrentById(current.id).first()
        assert(retrieved != null)
        assert(current.id == retrieved!!.id)
    }

    @Test
    fun saveCurrents_retrieveList() = runBlocking {
        val current1 = createFirstCurrent()
        currentDao.saveCurrent(current1)
        val current2 = createSecondCurrent()
        currentDao.saveCurrent(current2)
        val currents = currentDao.getCurrents().first()
        assert(currents.size == 2)
    }

    @Test
    fun saveCurrent_retrieveCurrent_deleteCurrent() = runBlocking {
        val current = createFirstCurrent()
        currentDao.saveCurrent(current)
        val saved = currentDao.getCurrentById(current.id).first()
        assert(saved != null)
        assert(saved!!.id == current.id)
        currentDao.deleteCurrent(current)
        val deleted = currentDao.getCurrentById(current.id).first()
        assert(deleted == null)
    }

    @Test
    fun saveCurrent_retrieveCurrent_updateCurrent() = runBlocking {
        val current = createFirstCurrent()
        currentDao.saveCurrent(current)
        val saved = currentDao.getCurrentById(current.id).first()
        assert(saved != null)
        assert(saved!!.id == current.id)
        val newDescription = "New description"
        currentDao.saveCurrent(saved.copy(description = newDescription))
        val updated = currentDao.getCurrentById(saved.id).first()
        assert(updated != null)
        assert(updated!!.description == newDescription)
    }

    @Test
    fun nothingSaved_retrieveNull() = runBlocking {
        val current = currentDao.getCurrentById(1).first()
        assert(current == null)
    }

    @Test
    fun nothingSaved_retrieveEmptyList() = runBlocking {
        val currents = currentDao.getCurrents().first()
        assert(currents.isEmpty())
    }
}