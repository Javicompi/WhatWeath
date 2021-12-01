package es.jnsoft.framework.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import es.jnsoft.framework.local.dao.CurrentDao
import es.jnsoft.framework.local.dao.DailyDao
import es.jnsoft.framework.local.dao.HourlyDao
import es.jnsoft.framework.local.model.CurrentEntity
import es.jnsoft.framework.local.model.DailyEntity
import es.jnsoft.framework.local.model.HourlyEntity

@Database(
    entities = [CurrentEntity::class, HourlyEntity::class, DailyEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun currentDao(): CurrentDao

    abstract fun hourlyDao(): HourlyDao

    abstract fun dailyDao(): DailyDao
}