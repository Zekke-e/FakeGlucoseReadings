package com.example.glucosereadings.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.glucosereadings.repositories.SensorRepository

class SensorManagementViewModel(private val repository: SensorRepository) : ViewModel() {

    val sensorState = repository.sensorState
    val egv = repository.egv

    private val _isAlertShowed = MutableLiveData(false)
    val isAlertShowed: LiveData<Boolean> get() = _isAlertShowed

    fun changeAlertVisibility(isAlertShowed:Boolean){
        _isAlertShowed.value = isAlertShowed
    }

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