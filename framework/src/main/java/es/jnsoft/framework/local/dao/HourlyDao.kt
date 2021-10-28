package es.jnsoft.framework.local.dao

import androidx.room.*
import es.jnsoft.framework.local.model.HourlyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HourlyDao {

    @Query("SELECT * FROM hourlies WHERE city_id = :cityId")
    fun getHourlies(cityId: Long): Flow<List<HourlyEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveHourlies(hourlies: List<HourlyEntity>)

    @Query("DELETE FROM hourlies WHERE city_id = :cityId")
    suspend fun deleteHourlies(cityId: Long)

    @Transaction
    suspend fun updateHourlies(hourlies: List<HourlyEntity>) {
        deleteHourlies(cityId = hourlies[0].cityId)
        saveHourlies(hourlies = hourlies)
    }
}