package com.example.glucosereadings.viewmodels

import androidx.lifecycle.ViewModel
import com.example.glucosereadings.repositories.SensorRepository

class SensorManagementViewModel(private val repository: SensorRepository): ViewModel() {

    val sensorState = repository.sensorState
    val egv = repository.egv

    fun addSensor() {
        repository.addSensor()
    }

    fun deleteSensor() {
        repository.deleteSensor()
    }

    fun setSensorLimit(limit: Int) {
        repository.setSensorLimit(limit)
    }

}