package com.example.taskinspectortest.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

// WorkManager の Worker 実装
class SimpleWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val tagName = "MyWorker"
        Log.d(tagName, "Worker started. Doing some background work...")
        delay(3000) // 3秒間処理をシミュレート
        Log.d(tagName, "Worker finished.")
        return Result.success()
    }
}