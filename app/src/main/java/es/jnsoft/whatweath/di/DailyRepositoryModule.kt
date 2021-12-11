package es.jnsoft.whatweath.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.jnsoft.data.local.DailyLocalDataSource
import es.jnsoft.data.remote.DailyRemoteDataSource
import es.jnsoft.data.repository.DailyRepositoryImp
import es.jnsoft.domain.repository.DailyRepository
import es.jnsoft.framework.local.DailyLocalDataSourceImp
import es.jnsoft.framework.remote.DailyRemoteDataSourceImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DailyRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDailyLocalDataSource(
        dataSource: DailyLocalDataSourceImp
    ): DailyLocalDataSource

    @Binds
    @Singleton
    abstract fun bindDailyRemoteDataSource(
        dataSource: DailyRemoteDataSourceImp
    ): DailyRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindDailyRepository(repository: DailyRepositoryImp): DailyRepository
}