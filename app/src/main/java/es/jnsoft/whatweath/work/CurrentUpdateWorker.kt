package es.jnsoft.whatweath.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import es.jnsoft.domain.repository.CurrentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

@HiltWorker
class CurrentUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: CurrentRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            val currents = repository.getCurrents().first()
            if (currents.isNotEmpty()) {
                val currentTime = System.currentTimeMillis()
                currents.map { current ->
                    if (currentTime - current.deltaTime >= TimeUnit.HOURS.toMillis(1)) {
                        val currentResult = repository.findCurrentById(current.id)
                        if (currentResult is es.jnsoft.domain.model.Result.Success) {
                            repository.saveCurrent(currentResult.value)
                        } else {
                            Result.retry()
                        }
                    }
                }
            } else {
                Result.success()
            }
            Result.success()
        } catch (ex: Exception) {
            Log.e(this.javaClass.simpleName, "Error updating currents", ex)
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "CurrentUpdateWorker"
    }
}