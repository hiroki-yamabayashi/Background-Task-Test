package com.example.taskinspectortest.worker

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log

// JobScheduler の JobService 実装 (API 21+)
@SuppressLint("SpecifyJobSchedulerIdRange")
class SimpleJobService : JobService() {
    private val tagName = "MyJob"
    override fun onStartJob(params: JobParameters?): Boolean {
        Log.d(tagName, "JobService started. Simulating work...")
        // UIスレッドをブロックしないように新しいスレッドで実行
        Thread {
            try {
                Thread.sleep(3000) // 3秒間処理をシミュレート
            } catch (e: InterruptedException) {
                Thread.currentThread().interrupt()
            }
            Log.d(tagName, "JobService finished.")
            jobFinished(params, false) // 処理完了をシステムに通知 (再スケジュール不要)
        }.start()
        return true // 作業が実行中であることを示す
    }

    override fun onStopJob(params: JobParameters): Boolean {
        Log.d(tagName, "JobService stopped unexpectedly.")
        return true // システムがジョブを再スケジュールすべきであることを示す
    }
}