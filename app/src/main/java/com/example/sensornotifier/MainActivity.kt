package com.example.sensornotifier

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.messaging.FirebaseMessaging

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
}