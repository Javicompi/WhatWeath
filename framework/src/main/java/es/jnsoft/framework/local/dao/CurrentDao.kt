package es.jnsoft.framework.local.dao

import androidx.room.*
import es.jnsoft.framework.local.model.CurrentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentDao {

    @Query("SELECT * FROM currents")
    suspend fun getCurrents(): List<CurrentEntity>

    @Query("SELECT * FROM currents WHERE id = :id")
    suspend fun getCurrentById(id: Long): CurrentEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrent(currentEntity: CurrentEntity)

    @Delete
    suspend fun deleteCurrent(currentEntity: CurrentEntity)
}