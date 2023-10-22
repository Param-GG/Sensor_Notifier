package com.example.sensornotifier

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class WebViewActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    // Register a BroadcastReceiver
    private val reloadReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // Reload the WebView when the broadcast is received
            webView.reload()
//            retrieveData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.web_view_activity)

        // Register the broadcast receiver
        val filter = IntentFilter("ReloadWebViewAction")
        registerReceiver(reloadReceiver, filter)

        val receivedIntent = intent
        val ipAddress = receivedIntent.getStringExtra("IpAddress")
        webView = findViewById(R.id.webView)

        webView.loadUrl("http://192.168.137.38/")
//        webView.loadUrl("www.ceeri.res.in/attendance-system/")
        val serviceIntent = Intent(this, ForegroundService::class.java)
        ContextCompat.startForegroundService(this, serviceIntent)

    }

//    fun retrieveData() {
//        TODO()
//    }

    override fun onDestroy() {
        super.onDestroy()

        // Unregister the broadcast receiver
        unregisterReceiver(reloadReceiver)
    }
}