package com.example.sensornotifier.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sensornotifier.utilities.SensorData

@Dao
interface SensValueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sValue: SensorData)

    @Delete
    suspend fun delete(value: SensorData)

    @Query("SELECT * FROM SensValueDB")
    fun getAll(): List<SensorData>

    @Query("SELECT * FROM SensValueDB where alarm= :alarm")
    suspend fun getFromAlarmState(alarm: Boolean): List<SensorData>
}