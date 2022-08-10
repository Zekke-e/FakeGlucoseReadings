package com.example.glucosereadings.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.glucosereadings.models.Cgm
import com.example.glucosereadings.utils.SensorStates

class SensorRepository private constructor() {

    private var cgm: Cgm? = null

    private val _sensorState = MutableLiveData(SensorStates.NOT_PRESENT)
    val sensorState: LiveData<SensorStates> get() = _sensorState

    companion object {
        private var instance: SensorRepository? = null

        fun getInstance(): SensorRepository = synchronized(this) {
            if (instance == null) {
                instance = SensorRepository()
            }
            instance!!
        }
    }

    fun addSensor() {
        if (cgm == null) {
            cgm = Cgm()
            _sensorState.postValue(SensorStates.PRESENT)
        }
    }

    fun deleteSensor() {
        if (cgm != null) {
            cgm = null
            _sensorState.postValue(SensorStates.NOT_PRESENT)
        }
    }

}