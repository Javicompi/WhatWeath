package es.jnsoft.framework.settings.datastore

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import es.jnsoft.domain.model.Units
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingsDataStore @Inject constructor(appContext: Context) {

    companion object {
        const val UNITS_KEY = "Units_Key"
        const val SELECTED_ID_KEY = "Selected_Id_Key"
    }

    private val settingsDataStore = appContext.dataStore

    private object PreferenceKeys {
        val UNITS_FORMAT = stringPreferencesKey(UNITS_KEY)
        val SELECTED_ID_FORMAT = longPreferencesKey(SELECTED_ID_KEY)
    }

    val unitsPreferences: Flow<String> = settingsDataStore.data.map { settings ->
        settings[PreferenceKeys.UNITS_FORMAT] ?: Units.STANDARD.value
    }

    suspend fun setUnits(units: Units) {
        settingsDataStore.edit { settings ->
            settings[PreferenceKeys.UNITS_FORMAT] = units.value
        }
    }

    val selectedIdPreferences: Flow<Long> = settingsDataStore.data.map { settings ->
        settings[PreferenceKeys.SELECTED_ID_FORMAT] ?: 0L
    }

    suspend fun setSelectedId(id: Long) {
        settingsDataStore.edit { settings ->
            settings[PreferenceKeys.SELECTED_ID_FORMAT] = id
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.NONE)
    suspend fun clearPreferences() {
        settingsDataStore.edit {
            it.clear()
        }
    }
}