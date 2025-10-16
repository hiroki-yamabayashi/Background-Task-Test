package com.example.taskinspectortest.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

/**
 * 最初に実行されるワーカー
 */
class InitialWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {
    val tag = "InitialWorker"
    override suspend fun doWork(): Result {
        Log.d(tag, "Worker started. Doing some background work...")
        delay(3000) // 3秒間処理をシミュレート
        Log.d(tag, "Worker finished.")
        return Result.success()
    }
}