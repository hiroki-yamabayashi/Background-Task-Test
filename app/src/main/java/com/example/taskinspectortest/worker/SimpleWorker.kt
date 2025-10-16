package com.example.taskinspectortest.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.taskinspectortest.util.ToastController
import kotlinx.coroutines.delay

// WorkManager の Worker 実装
class SimpleWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    val toastController = ToastController(context)
    override suspend fun doWork(): Result {
        "MyWorker"
        toastController.showLatestToast(message = "Worker started. Doing some background work...")
        delay(3000) // 3秒間処理をシミュレート
        toastController.showLatestToast(message = "Worker finished.")
        return Result.success()
    }
}