package es.jnsoft.framework.settings

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import es.jnsoft.domain.enums.Units
import es.jnsoft.framework.settings.datastore.SettingsDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class SettingsDataStoreTest {

    private lateinit var settings: SettingsDataStore

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        settings = SettingsDataStore(context)
    }

    @After
    fun clear() = runBlocking {
        settings.clearPreferences()
    }

    @Test
    fun getUnits_defaultValue() = runBlocking {
        val units = settings.unitsPreferences.first()
        assert(units == Units.STANDARD.value)
    }

    @Test
    fun saveUnit_getUnit() = runBlocking {
        val units = Units.METRIC
        settings.setUnits(units)
        val retrieved = settings.unitsPreferences.first()
        assert(units.value == retrieved)
    }

    @Test
    fun saveUnit_updateUnit() = runBlocking {
        val unit1 = Units.METRIC
        settings.setUnits(unit1)
        val retrieved1 = settings.unitsPreferences.first()
        assert(unit1.value == retrieved1)
        val unit2 = Units.IMPERIAL
        settings.setUnits(unit2)
        val retrieved2 = settings.unitsPreferences.first()
        assert(unit2.value == retrieved2)
    }

    @Test
    fun getSelectedId_defaultValue() = runBlocking {
        val selectedId = settings.selectedIdPreferences.first()
        assert(selectedId == 0L)
    }

    @Test
    fun saveSelectedId_getSelectedId() = runBlocking {
        val id = 6697298L
        settings.setSelectedId(id)
        val savedId = settings.selectedIdPreferences.first()
        assert(id == savedId)
    }

    @Test
    fun saveSelectedId_updateSelectedId() = runBlocking {
        val id = 6697298L
        settings.setSelectedId(id)
        val savedId = settings.selectedIdPreferences.first()
        assert(id == savedId)
        val newId = 7228796L
        settings.setSelectedId(newId)
        val newSavedId = settings.selectedIdPreferences.first()
        assert(newId == newSavedId)
    }
}