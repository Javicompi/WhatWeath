package es.jnsoft.whatweath.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.jnsoft.data.local.HourlyLocalDataSource
import es.jnsoft.data.remote.HourlyRemoteDataSource
import es.jnsoft.data.repository.HourlyRepositoryImp
import es.jnsoft.domain.repository.HourlyRepository
import es.jnsoft.framework.local.HourlyLocalDataSourceImp
import es.jnsoft.framework.remote.HourlyRemoteDataSourceImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HourlyRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindHourlyLocalDataSource(
        dataSource: HourlyLocalDataSourceImp
    ): HourlyLocalDataSource

    @Binds
    @Singleton
    abstract fun bindHourlyRemoteDataSource(
        dataSource: HourlyRemoteDataSourceImp
    ): HourlyRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindHourlyRepository(repository: HourlyRepositoryImp): HourlyRepository
}