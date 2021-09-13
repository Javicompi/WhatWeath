package es.jnsoft.framework.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import es.jnsoft.framework.local.dao.CurrentDao
import es.jnsoft.framework.local.model.CurrentEntity

@Database(entities = [CurrentEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currentDao(): CurrentDao
}