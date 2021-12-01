package es.jnsoft.framework.local.dao

import androidx.room.*
import es.jnsoft.framework.local.model.DailyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DailyDao {

    @Query("SELECT * FROM dailies WHERE latitude = :lat AND longitude = :lon")
    fun getDailies(lat: Double, lon: Double): Flow<List<DailyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDailies(dailies: List<DailyEntity>)

    @Query("DELETE FROM dailies WHERE latitude = :lat AND longitude = :lon")
    suspend fun deleteDailies(lat: Double, lon: Double)

    @Transaction
    suspend fun updateDailies(dailies: List<DailyEntity>) {
        val lat = dailies[0].latitude
        val lon = dailies[0].longitude
        deleteDailies(lat = lat, lon = lon)
        saveDailies(dailies = dailies)
    }
}