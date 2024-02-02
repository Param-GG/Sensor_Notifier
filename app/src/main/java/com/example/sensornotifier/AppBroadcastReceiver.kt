package com.example.sensornotifier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.sensornotifier.services.ForegroundService
import com.example.sensornotifier.services.LocalNotificationService

class AppBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "STOP_SERVICES") {
            // Stop your services
            val stopForeServiceIntent = Intent(context, ForegroundService::class.java)
            val stopNotifServiceIntent = Intent(context, LocalNotificationService::class.java)
            context?.stopService(stopForeServiceIntent)
            context?.stopService(stopNotifServiceIntent)
        }
    }
}