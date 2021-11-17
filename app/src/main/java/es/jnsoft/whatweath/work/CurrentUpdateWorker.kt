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
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

@HiltWorker
class CurrentUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters,
    private val repository: CurrentRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            repository.updateCurrents()
            val format = SimpleDateFormat("HH:mm", Locale.getDefault())
            val message = format.format(Date(System.currentTimeMillis()))
            Log.d(this.javaClass.simpleName, "CurrentUpdateWorker executed at: $message")
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