package com.example.sensornotifier.respositories

import com.example.sensornotifier.db.SensValueDB
import com.example.sensornotifier.db.SensValueDao
import com.example.sensornotifier.utilities.SensorData

class SensValueRepo(private val sValueDB: SensValueDB) {
    fun getAll(): List<SensorData> {
        return sValueDB.sensValueDao().getAll()
    }

    suspend fun insert(sValue: SensorData) {
        sValueDB.sensValueDao().insert(sValue)
    }

    suspend fun delete(sValue: SensorData) {
        sValueDB.sensValueDao().delete(sValue)
    }

    suspend fun getFromAlarmState(alarm: Boolean): List<SensorData> {
        return sValueDB.sensValueDao().getFromAlarmState(alarm)
    }

}