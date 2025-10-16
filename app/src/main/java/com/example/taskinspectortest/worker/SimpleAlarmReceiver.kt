package com.example.taskinspectortest.worker

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

// AlarmManager の BroadcastReceiver 実装
class SimpleAlarmReceiver : BroadcastReceiver() {
    private val tag = "MyAlarm"
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(tag, "Alarm received. Performing immediate task...")
        // アラームは短いタスクにのみ使用すべき
        Log.d(tag, "Alarm task finished.")
        // Wakelock を取得する場合は、onReceive() の終了時に解放されるように注意
    }
}