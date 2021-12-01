package es.jnsoft.whatweath.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import es.jnsoft.data.local.DailyLocalDataSource
import es.jnsoft.data.remote.DailyRemoteDataSource
import es.jnsoft.data.repository.DailyRepositoryImp
import es.jnsoft.domain.repository.DailyRepository
import es.jnsoft.framework.local.DailyLocalDataSourceImp
import es.jnsoft.framework.remote.DailyRemoteDataSourceImp

@Module
@InstallIn(ViewModelComponent::class)
abstract class DailyRepositoryModule {

    @Binds
    @ViewModelScoped
    abstract fun bindDailyLocalDataSource(
        dataSource: DailyLocalDataSourceImp
    ): DailyLocalDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindDailyRemoteDataSource(
        dataSource: DailyRemoteDataSourceImp
    ): DailyRemoteDataSource

    @Binds
    @ViewModelScoped
    abstract fun bindDailyRepository(repository: DailyRepositoryImp): DailyRepository
}