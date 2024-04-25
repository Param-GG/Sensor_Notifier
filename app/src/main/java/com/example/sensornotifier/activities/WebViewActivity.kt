package com.example.sensornotifier.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.example.sensornotifier.R
import com.example.sensornotifier.db.SensValueDB
import com.example.sensornotifier.utilities.SensorData
import com.example.sensornotifier.respositories.SensValueRepo
import com.example.sensornotifier.services.ForegroundService
import com.example.sensornotifier.viewModels.DbViewModel
import com.example.sensornotifier.viewModels.SensorViewModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okio.IOException

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var url: String
    private lateinit var data: SensorData
    private var context = this
    private var reloadReceiver: BroadcastReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view_activity)

        // Load URL
        val receivedIntent = intent
        val ipAddress = receivedIntent.getStringExtra("IpAddress")
        webView = findViewById(R.id.webView)

        url = "http://192.168.137.197/"
//        url = "http://$ipAddress/"
//        url = "https://www.google.com/"

        webView.loadUrl(url)
//        webView.loadUrl("www.ceeri.res.in/attendance-system/")

        // Start foreground service
        val serviceIntent = Intent(this, ForegroundService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)
    }

    override fun onStart() {
        super.onStart()

        // Register a BroadcastReceiver
        reloadReceiver = object : BroadcastReceiver() {

            // Initialise database
            val db = Room.databaseBuilder(context, SensValueDB::class.java, "SensValueDB")
                .fallbackToDestructiveMigration()
                .build()

            val repo = SensValueRepo(db)
            val dbViewModel = DbViewModel(repo)
            val sensorViewModel = SensorViewModel()

            override fun onReceive(context: Context?, intent: Intent?) {
                // Reload the WebView when the broadcast is received
                webView.reload()
                try {
                    data = sensorViewModel.retrieveData(url)

                    if (data.temp > data.tempThreshold || data.humidity > data.humidityThreshold) {
                        // Set alarm status
                        data.alarm = true
                    }

                    val sendDataIntent = Intent("SendDataToService")
                    sendDataIntent.putExtra("alarm", data.alarm)

                    sendBroadcast(sendDataIntent)

                    dbViewModel.insertDataPeriodically(data)
                }
                catch (_:Exception) {}
            }
        }

        val filter = IntentFilter("ReloadWebViewAction")
        registerReceiver(reloadReceiver, filter)

    }

    override fun onDestroy() {
        super.onDestroy()

        // Unregister the broadcast receiver
        reloadReceiver?.let { unregisterReceiver(it) }
    }
}