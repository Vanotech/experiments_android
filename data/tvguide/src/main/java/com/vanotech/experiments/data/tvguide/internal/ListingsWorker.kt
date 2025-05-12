package com.vanotech.experiments.data.tvguide.internal

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.vanotech.experiments.data.tvguide.ListingRepo
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import java.util.Calendar
import java.util.concurrent.TimeUnit

internal class ListingsWorker(
    appContext: Context,
    params: WorkerParameters
) : CoroutineWorker(appContext, params) {

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface GetListingWorkerEntryPoint {
        fun listingRepo(): ListingRepo

        companion object {
            fun getListingRepo(context: Context): ListingRepo {
                return EntryPointAccessors.fromApplication(
                    context,
                    GetListingWorkerEntryPoint::class.java
                ).listingRepo()
            }
        }
    }

    override suspend fun doWork(): Result {
        val listingRepo = GetListingWorkerEntryPoint.getListingRepo(applicationContext)
        val result = listingRepo.getListings(12)
        return when (result.isSuccess) {
            true -> Result.success()
            else -> Result.retry()
        }
    }

    companion object {
        private const val UNIQUE_NAME_ONE_TIME = "a817df7c-a2ad-4277-bcba-eb49027577b9"
        private const val UNIQUE_NAME_PERIODIC = "ee6bd140-3bd8-4159-a58d-f9409be336f5"

        fun enqueue(context: Context) {
            val workManager = WorkManager.getInstance(context)

            val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val oneTimeWorkRequest = OneTimeWorkRequestBuilder<ListingsWorker>()
                .setConstraints(constraints)
                .build()

            workManager.enqueueUniqueWork(
                UNIQUE_NAME_ONE_TIME,
                ExistingWorkPolicy.KEEP,
                oneTimeWorkRequest
            )

            val calendar = Calendar.getInstance()
            val now = calendar.timeInMillis
            val initialTime = calendar.apply {
                set(Calendar.MILLISECOND, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MINUTE, 0)
                add(Calendar.HOUR_OF_DAY, 1)
            }.timeInMillis
            val initialDelay = initialTime - now

            val periodicWorkRequest = PeriodicWorkRequestBuilder<ListingsWorker>(1, TimeUnit.HOURS)
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .setConstraints(constraints)
                .build()

            workManager.enqueueUniquePeriodicWork(
                UNIQUE_NAME_PERIODIC,
                ExistingPeriodicWorkPolicy.UPDATE,
                periodicWorkRequest
            )
        }
    }
}