package com.example.sensornotifier

import com.google.firebase.database.DatabaseReference

class DatabaseFunctions {

    fun setSensorState(dbRef : DatabaseReference) {
        dbRef.setValue(1)
    }
    fun resetSensorState(dbRef: DatabaseReference) {
        dbRef.setValue(0)
    }
}