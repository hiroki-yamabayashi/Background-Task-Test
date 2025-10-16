package com.example.taskinspectortest.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.taskinspectortest.util.ToastController

// AlarmManager の BroadcastReceiver 実装
class SimpleAlarmReceiver : BroadcastReceiver() {
    private val tagName = "MyAlarm"
    override fun onReceive(context: Context?, intent: Intent?) {
        val toastController = ToastController(context)
        toastController.showLatestToast("Alarm received. Performing immediate task...")
        // アラームは短いタスクにのみ使用すべき
        toastController.showLatestToast("Alarm task finished.")
        // Wakelock を取得する場合は、onReceive() の終了時に解放されるように注意
    }
}