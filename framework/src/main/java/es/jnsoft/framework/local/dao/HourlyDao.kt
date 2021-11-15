package es.jnsoft.framework.local.dao

import androidx.room.*
import es.jnsoft.framework.local.model.HourlyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HourlyDao {

    @Query("SELECT * FROM hourlies WHERE latitude = :lat AND longitude = :lon")
    fun getHourlies(lat: Double, lon: Double): Flow<List<HourlyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHourlies(hourlies: List<HourlyEntity>)

    @Query("DELETE FROM hourlies WHERE latitude = :lat AND longitude = :lon")
    suspend fun deleteHourlies(lat: Double, lon: Double)
    /*@Delete
    suspend fun deleteHourlies(hourlies: List<HourlyEntity>)*/

    @Transaction
    suspend fun updateHourlies(hourlies: List<HourlyEntity>) {
        val lat = hourlies[0].latitude
        val lon = hourlies[0].longitude
        deleteHourlies(lat = lat, lon = lon)
        saveHourlies(hourlies = hourlies)
    }
}