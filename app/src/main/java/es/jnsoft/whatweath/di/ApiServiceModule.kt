package es.jnsoft.whatweath.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import es.jnsoft.framework.remote.service.CurrentApiService
import es.jnsoft.framework.remote.service.HourlyApiService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiServiceModule {

    @Provides
    @Singleton
    fun provideCurrentApiService(retrofit: Retrofit): CurrentApiService {
        return retrofit.create(CurrentApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideHourlyApiService(retrofit: Retrofit): HourlyApiService {
        return retrofit.create(HourlyApiService::class.java)
    }
}