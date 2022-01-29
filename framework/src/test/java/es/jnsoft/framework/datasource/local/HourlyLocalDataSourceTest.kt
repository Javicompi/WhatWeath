package es.jnsoft.framework.datasource.local

import es.jnsoft.framework.local.HourlyLocalDataSourceImp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class HourlyLocalDataSourceTest {

    private lateinit var hourlyDao: FakeHourlyDao

    private lateinit var dataSource: HourlyLocalDataSourceImp

    @Before
    fun setup() {
        hourlyDao = FakeHourlyDao()
        dataSource = HourlyLocalDataSourceImp(hourlyDao)
    }

    @Test
    fun emptyHourlies_retrieveEmptyList() = runBlocking {
        val retrieved = dataSource.getHourlies(35.555, 5.555).first()
        assert(retrieved.isEmpty())
    }

    @Test
    fun saveHourlies_retrieveHourlies() = runBlocking {
        val hourlies = createHourlyList()
        dataSource.saveHourlies(hourlies)
        val retrieved = dataSource.getHourlies(hourlies[0].lat, hourlies[0].lon).first()
        assert(retrieved.isNotEmpty())
        assert(retrieved[0].lat == hourlies[0].lat && retrieved[0].lon == hourlies[0].lon)
    }

    @Test
    fun saveHourlies_retrieveHourlies_deleteHourlies() = runBlocking {
        val hourlies = createHourlyList()
        dataSource.saveHourlies(hourlies)
        val retrieved = dataSource.getHourlies(hourlies[0].lat, hourlies[0].lon).first()
        assert(retrieved.isNotEmpty() && retrieved.size == 3)
        val lat = retrieved[0].lat
        val lon = retrieved[0].lon
        dataSource.deleteHourlies(lat, lon)
        val emptyData = dataSource.getHourlies(hourlies[0].lat, hourlies[0].lon).first()
        assert(emptyData.isEmpty())
    }
}