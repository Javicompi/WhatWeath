package es.jnsoft.whatweath.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.jnsoft.data.repository.SettingsRepositoryImp
import es.jnsoft.data.settings.SettingsDataSource
import es.jnsoft.domain.repository.SettingsRepository
import es.jnsoft.framework.settings.SettingsDataSourceImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SettingsRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindSettingsDataSource(
        dataSource: SettingsDataSourceImp
    ): SettingsDataSource

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(repository: SettingsRepositoryImp): SettingsRepository
}