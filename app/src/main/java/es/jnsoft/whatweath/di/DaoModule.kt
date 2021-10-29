package es.jnsoft.whatweath.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import es.jnsoft.framework.local.dao.CurrentDao
import es.jnsoft.framework.local.dao.HourlyDao
import es.jnsoft.framework.local.database.AppDatabase

@Module
@InstallIn(ViewModelComponent::class)
object DaoModule {

    @Provides
    @ViewModelScoped
    fun provideCurrentDao(database: AppDatabase): CurrentDao {
        return database.currentDao()
    }

    @Provides
    @ViewModelScoped
    fun provideHourlyDao(database: AppDatabase): HourlyDao {
        return database.hourlyDao()
    }
}