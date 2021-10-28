package es.jnsoft.framework.datasource.local

import es.jnsoft.framework.local.HourlyLocalDataSourceImp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
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
    fun saveHourlies_retrieveHourlies() = runBlocking {
        val cityId = 7214801L
        val hourlies = createHourlyList(cityId)
        //hourlyDao.saveHourlies(hourlies.mapToEntityList())
        dataSource.saveHourlies(hourlies)
        //val retrieved = hourlyDao.getHourlies(cityId).first()
        val retrieved = dataSource.getHourlies(cityId).first()
        assert(retrieved.isNotEmpty())
        assert(retrieved[0].cityId == cityId)
    }
}