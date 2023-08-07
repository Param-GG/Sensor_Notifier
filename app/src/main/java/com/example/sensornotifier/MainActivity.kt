package com.example.sensornotifier

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
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

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("Token", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d("Token", "The token is: $token")
        })

        val onBtn = findViewById<Button>(R.id.onBtn)
        val offBtn = findViewById<Button>(R.id.offBtn)

        val database = FirebaseDatabase.getInstance()
        val dbReference = database.getReference("sensor state")
        val dbFns = DatabaseFunctions()

        onBtn.setOnClickListener {
            dbFns.setSensorState(dbReference)
        }

        offBtn.setOnClickListener {
            dbFns.resetSensorState(dbReference)
        }
    }
}