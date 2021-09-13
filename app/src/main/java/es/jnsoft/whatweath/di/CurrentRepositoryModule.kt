package es.jnsoft.whatweath.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import es.jnsoft.data.local.CurrentLocalDataSource
import es.jnsoft.data.remote.CurrentRemoteDataSource
import es.jnsoft.data.repository.CurrentRepositoryImp
import es.jnsoft.domain.repository.CurrentRepository
import es.jnsoft.framework.local.CurrentLocalDataSourceImp
import es.jnsoft.framework.remote.CurrentRemoteDataSourceImp

@Module
@InstallIn(ViewModelComponent::class)
abstract class CurrentRepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindCurrentLocalDataSource(
        dataSource: CurrentLocalDataSourceImp
    ): CurrentLocalDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindCurrentRemoteDataSource(
        dataSource: CurrentRemoteDataSourceImp
    ): CurrentRemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindCurrentRepository(repository: CurrentRepositoryImp): CurrentRepository
}