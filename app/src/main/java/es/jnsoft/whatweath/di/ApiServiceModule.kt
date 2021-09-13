package es.jnsoft.whatweath.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import es.jnsoft.framework.remote.service.CurrentApiService
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
object ApiServiceModule {

    @Provides
    @ViewModelScoped
    fun provideCurrentApiService(retrofit: Retrofit): CurrentApiService {
        return retrofit.create(CurrentApiService::class.java)
    }
}