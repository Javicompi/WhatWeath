package es.jnsoft.whatweath.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.jnsoft.framework.local.dao.CurrentDao
import es.jnsoft.framework.local.dao.DailyDao
import es.jnsoft.framework.local.dao.HourlyDao
import es.jnsoft.framework.local.database.AppDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideCurrentDao(database: AppDatabase): CurrentDao {
        return database.currentDao()
    }

    @Provides
    @Singleton
    fun provideHourlyDao(database: AppDatabase): HourlyDao {
        return database.hourlyDao()
    }

    @Provides
    @Singleton
    fun provideDailyDao(database: AppDatabase): DailyDao {
        return database.dailyDao()
    }
}