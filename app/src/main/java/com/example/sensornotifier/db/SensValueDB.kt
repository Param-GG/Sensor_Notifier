package com.example.sensornotifier.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sensornotifier.utilities.SensorData

@Database(entities = [SensorData::class], version = 2)
abstract class SensValueDB: RoomDatabase() {
    abstract fun sensValueDao(): SensValueDao
}