package es.jnsoft.whatweath.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.jnsoft.data.local.CurrentLocalDataSource
import es.jnsoft.data.remote.CurrentRemoteDataSource
import es.jnsoft.data.repository.CurrentRepositoryImp
import es.jnsoft.domain.repository.CurrentRepository
import es.jnsoft.framework.local.CurrentLocalDataSourceImp
import es.jnsoft.framework.remote.CurrentRemoteDataSourceImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CurrentRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCurrentLocalDataSource(
        dataSource: CurrentLocalDataSourceImp
    ): CurrentLocalDataSource

    @Binds
    @Singleton
    abstract fun bindCurrentRemoteDataSource(
        dataSource: CurrentRemoteDataSourceImp
    ): CurrentRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindCurrentRepository(repository: CurrentRepositoryImp): CurrentRepository
}