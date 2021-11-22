package es.jnsoft.whatweath

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import dagger.hilt.android.HiltAndroidApp
import es.jnsoft.whatweath.work.CurrentUpdateWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class WhatWeathApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    private val applicationScope = CoroutineScope(Dispatchers.Main)

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        applicationScope.launch {
            delayedInit()
        }
    }

    private fun delayedInit() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()
        val repeatingRequest =
            PeriodicWorkRequestBuilder<CurrentUpdateWorker>(
                60, TimeUnit.MINUTES,
                45, TimeUnit.MINUTES
            ).setConstraints(constraints)
                .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            CurrentUpdateWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}