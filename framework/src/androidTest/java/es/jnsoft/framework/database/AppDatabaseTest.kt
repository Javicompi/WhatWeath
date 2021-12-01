package es.jnsoft.framework.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import es.jnsoft.framework.local.dao.CurrentDao
import es.jnsoft.framework.local.dao.HourlyDao
import es.jnsoft.framework.local.database.AppDatabase
import es.jnsoft.framework.mapper.mapToEntityList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class AppDatabaseTest {

    private lateinit var database: AppDatabase

    private lateinit var currentDao: CurrentDao

    private lateinit var hourlyDao: HourlyDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context,
            AppDatabase::class.java
        ).build()
        currentDao = database.currentDao()
        hourlyDao = database.hourlyDao()
    }

    @After
    fun closeDb() {
        database.close()
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
    fun saveHourlies_retrieveHourlies() = runBlocking {
        val lat = 30.00
        val lon = 50.00
        val hourlies = createHourlyList(lat, lon)
        hourlyDao.saveHourlies(hourlies.mapToEntityList())
        val retrieved = hourlyDao.getHourlies(lat, lon).first()
        assert(retrieved.isNotEmpty())
        assert((retrieved[0].latitude == lat) && (retrieved[0].longitude == lon))
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
    fun saveMultipleHourlies_retrieveSelectedHourlies() = runBlocking {
        val firstLat = 10.00
        val firstLon = 20.00
        val secondLat = 30.00
        val secondLon = 40.00
        val firstHourlies = createHourlyList(firstLat, firstLon)
        val secondHourlies = createHourlyList(secondLat, secondLon)
        hourlyDao.saveHourlies(firstHourlies.mapToEntityList())
        hourlyDao.saveHourlies(secondHourlies.mapToEntityList())
        val firstHourliesSaved = hourlyDao.getHourlies(firstLat, firstLon).first()
        assert(firstHourliesSaved.size == 3)
        assert(firstHourliesSaved[0].latitude == firstLat)
        assert(firstHourliesSaved[0].longitude == firstLon)
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
    fun saveHourlies_retrieveHourlies_deleteHourlies() = runBlocking {
        val lat = 30.00
        val lon = 40.00
        val hourlies = createHourlyList(lat, lon)
        hourlyDao.saveHourlies(hourlies.mapToEntityList())
        val savedHourlies = hourlyDao.getHourlies(lat, lon).first()
        assert(savedHourlies.size == 3)
        assert((savedHourlies[0].latitude == lat) && (savedHourlies[0].longitude == lon))
        hourlyDao.deleteHourlies(lat, lon)
        val deletedHourlies = hourlyDao.getHourlies(lat, lon).first()
        assert(deletedHourlies.isEmpty())
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
    fun saveHourlies_retrieveHourlies_updateHourlies() = runBlocking {
        val lat = 30.00
        val lon = 40.00
        val hourlies = createHourlyList(lat, lon)
        hourlyDao.saveHourlies(hourlies.mapToEntityList())
        val savedHourlies = hourlyDao.getHourlies(lat, lon).first()
        assert(savedHourlies.size == 3)
        assert((savedHourlies[0].latitude == lat) && (savedHourlies[0].longitude == lon))
        val newHourlies = savedHourlies.map { hourly ->
            hourly.copy(deltaTime = hourly.deltaTime + 3600000)
        }
        hourlyDao.updateHourlies(newHourlies)
        val updatedHourlies = hourlyDao.getHourlies(lat, lon).first()
        assert(updatedHourlies.size == 3)
        assert((updatedHourlies[0].latitude == lat) && (updatedHourlies[0].longitude == lon))
        assert(updatedHourlies[0].deltaTime > hourlies[0].deltaTime)
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

    @Test
    fun nothingSave_retrieveEmptyList() = runBlocking {
        val hourlies = hourlyDao.getHourlies(10.00, 20.00).first()
        assert(hourlies.isEmpty())
    }
}