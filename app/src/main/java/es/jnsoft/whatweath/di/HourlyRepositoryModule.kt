package es.jnsoft.whatweath.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import es.jnsoft.data.local.HourlyLocalDataSource
import es.jnsoft.data.remote.HourlyRemoteDataSource
import es.jnsoft.data.repository.HourlyRepositoryImp
import es.jnsoft.domain.repository.HourlyRepository
import es.jnsoft.framework.local.HourlyLocalDataSourceImp
import es.jnsoft.framework.remote.HourlyRemoteDataSourceImp

@Module
@InstallIn(ViewModelComponent::class)
abstract class HourlyRepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindHourlyLocalDataSource(
        dataSource: HourlyLocalDataSourceImp
    ): HourlyLocalDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindHourlyRemoteDataSource(
        dataSource: HourlyRemoteDataSourceImp
    ): HourlyRemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindHourlyRepository(repository: HourlyRepositoryImp): HourlyRepository
}