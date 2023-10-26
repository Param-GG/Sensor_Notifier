package com.example.sensornotifier.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.example.sensornotifier.R
import com.example.sensornotifier.utilities.SensorData
import java.util.Timer
import java.util.TimerTask

class ForegroundService: Service() {

    private lateinit var timer: Timer
    private val handler = Handler(Looper.getMainLooper())

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "foreground_channel_id",
                "Foreground Notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            val context = applicationContext
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }

        // Build the foreground notification
        val notification: Notification = NotificationCompat.Builder(this, "foreground_channel_id")
            .setContentTitle("Foreground Service")
            .setContentText("Running in the background")
            .setSmallIcon(R.drawable.ic_notification_foreground)
//            .setContentIntent(PendingIntent.getActivity(
//                this, 1, intent,
//                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE))
            .build()

        // Start the service as a foreground service
        startForeground(2, notification)

        // Register the broadcast receiver
        val filter = IntentFilter("SendDataToService")
        registerReceiver(dataReceiver, filter)

        // Schedule a task to refresh the WebView every 15 minutes
        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                handler.post {
                    // Trigger a broadcast to reload the WebView
                    val reloadIntent = Intent("ReloadWebViewAction")
                    sendBroadcast(reloadIntent)
                }
            }
        }, 0, 1 * 60 * 1000) // 15 minutes in milliseconds

        return START_STICKY

    }

    // Register broadcast receiver
    private val dataReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, i1: Intent?) {
            if (i1?.action == "SendDataToService") {
                val alarmStatus = i1.getBooleanExtra("alarm",false)

                // Process the data received from the activity
                val localNotificationService = LocalNotificationService()
                if (alarmStatus) {
                    // Show the notification using the SensorNotificationService
                    localNotificationService.showNotification(applicationContext.applicationContext)
                }
            }
        }
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(dataReceiver)
    }

}