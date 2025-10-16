package com.example.taskinspectortest.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.taskinspectortest.util.ToastController
import com.example.taskinspectortest.worker.SimpleAlarmReceiver
import com.example.taskinspectortest.worker.SimpleJobService
import com.example.taskinspectortest.worker.SimpleWorker
import java.util.concurrent.TimeUnit

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun TaskInspectorScreen() {
    val context = LocalContext.current
    val toastController = remember { ToastController(context) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
    ) {
        // --- 1. Workers (WorkManager) ---
        Button(onClick = {
            val workRequest = OneTimeWorkRequestBuilder<SimpleWorker>().addTag("SimpleWorkTag")
                .setInitialDelay(2, TimeUnit.SECONDS) // 2秒後に開始
                .build()

            WorkManager.getInstance(context).enqueue(workRequest)
            toastController.showLatestToast(message = "Worker Enqueued: SimpleWorker")
        }) {
            Text("1. Schedule Worker (WorkManager)")
        }

        HorizontalDivider()

        // --- 2. Jobs (JobScheduler) ---
        Button(onClick = {
            val jobId = 123
            val jobScheduler =
                context.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
            val componentName = ComponentName(context, SimpleJobService::class.java)

            val jobInfo = JobInfo.Builder(jobId, componentName).setMinimumLatency(1000) // 1秒後に実行可能
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // ネットワークが必要な設定
                .build()

            val resultCode = jobScheduler.schedule(jobInfo)
            if (resultCode == JobScheduler.RESULT_SUCCESS) {
                toastController.showLatestToast(message = "Job Scheduled: SimpleJobService")
            } else {
                toastController.showLatestToast(message = "Job Scheduled: failed")
            }
        }) {
            Text("2. Schedule Job (JobScheduler)")
        }

        HorizontalDivider()

        // --- 3. Alarms (AlarmManager) ---
        Button(onClick = {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, SimpleAlarmReceiver::class.java)
            val pendingIntent = PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // 5秒後に正確に起動するアラームを設定 (RTC_WAKEUP はデバイスをスリープ解除する)
            val triggerTime = System.currentTimeMillis() + 5000

            if (alarmManager.canScheduleExactAlarms()) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent
                )
                toastController.showLatestToast(message = "Alarm Set: RTC_WAKEUP (Exact)")
            } else {
                toastController.showLatestToast(message = "Cannot schedule exact alarms. Check permissions/settings.")
                // 権限がない場合は設定画面へ誘導
                toastController.showLatestToast(message = "Exact alarm permission is required. Opening settings...")
                // 設定画面を開くためのIntentを作成
                Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM).also {
                    context.startActivity(it)
                }
            }
        }) {
            Text("3. Schedule Alarm (AlarmManager)")
        }

        HorizontalDivider()

        // --- 4. Wakelocks (PowerManager) ---
        Button(onClick = {
            val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
            // PARTIAL_WAKE_LOCK は画面がオフでもCPUを稼働させ続ける
            val wakeLock: PowerManager.WakeLock = powerManager.newWakeLock(
                PowerManager.PARTIAL_WAKE_LOCK,
                "MyApp:MyTestWakelockTag" // Inspectorで確認するための識別可能なタグ
            )

            wakeLock.acquire(10000L) // 10秒間 wakelock を保持
            toastController.showLatestToast(message = "Wakelock Acquired for 10 seconds: MyTestWakelockTag")

            // 10秒後に自動的に解放される (acquire(timeout) を使用)
            // 手動で解放する場合は .acquire() の後に .release() を適切なタイミングで呼び出す
        }) {
            Text("4. Acquire Wakelock (10s)")
        }
    }
}