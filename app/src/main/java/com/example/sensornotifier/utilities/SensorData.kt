package com.example.sensornotifier.utilities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SensValueDB")
data class SensorData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "temperature") var temp: Double = 0.0,
    @ColumnInfo(name = "humidity") var humidity: Double = 0.0,
    @ColumnInfo(name = "temp threshold") val tempThreshold: Double =50.0,
    @ColumnInfo(name = "humidity threshold") val humidityThreshold: Double = 70.0,
    @ColumnInfo(name = "time") var time: String = "0",
    @ColumnInfo(name = "alarm") var alarm: Boolean = false
)
