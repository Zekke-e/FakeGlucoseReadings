package com.example.glucosereadings.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.glucosereadings.models.Cgm
import com.example.glucosereadings.utils.SensorStates
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers.io

class SensorRepository private constructor() {

    private var cgm: Cgm? = null
    private var egvDisposable: Disposable? = null

    private val _egv = MutableLiveData<Int?>(null)
    val egv: LiveData<Int?> get() = _egv

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
            cgm = Cgm().also { sensor ->
                egvDisposable = sensor.getEgvObservable()
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        _egv.value = it
                    }
            }
            _sensorState.postValue(SensorStates.PRESENT)
        }
    }

    fun deleteSensor() {
        if (cgm != null) {
            egvDisposable?.dispose()
            cgm = null
            _egv.value = null
            _sensorState.postValue(SensorStates.NOT_PRESENT)
        }
    }

    fun setSensorLimit(limit: Int) {
        cgm?.setEgvLimit(limit)
    }

}