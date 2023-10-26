package com.example.sensornotifier.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sensornotifier.respositories.SensValueRepo
import com.example.sensornotifier.utilities.SensorData
import kotlinx.coroutines.launch

class DbViewModel(private val repository: SensValueRepo): ViewModel() {
    fun insertDataPeriodically(sValue: SensorData) {
        viewModelScope.launch {
            repository.insert(sValue)
        }
    }
}