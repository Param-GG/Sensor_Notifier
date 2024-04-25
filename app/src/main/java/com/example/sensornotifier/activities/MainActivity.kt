package com.example.sensornotifier.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.sensornotifier.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etValue = findViewById<EditText>(R.id.editText)
        val btn = findViewById<Button>(R.id.btn)

        btn.setOnClickListener {
            val enteredIP = etValue.text.toString()
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("IpAddress",enteredIP)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()

        val stopServicesIntent = Intent("STOP_SERVICES")
        sendBroadcast(stopServicesIntent)
    }

    override fun onRestart() {
        super.onRestart()

        val stopServicesIntent = Intent("STOP_SERVICES")
        sendBroadcast(stopServicesIntent)
    }
}