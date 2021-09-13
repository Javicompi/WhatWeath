package es.jnsoft.framework.local.dao

import androidx.room.*
import es.jnsoft.framework.local.model.CurrentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentDao {

    @Query("SELECT * FROM currents")
    fun getCurrents(): Flow<List<CurrentEntity>>

    @Query("SELECT * FROM currents WHERE id = :id")
    fun getCurrentById(id: Long): Flow<CurrentEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCurrent(currentEntity: CurrentEntity)

    @Delete
    suspend fun deleteCurrent(currentEntity: CurrentEntity)
}