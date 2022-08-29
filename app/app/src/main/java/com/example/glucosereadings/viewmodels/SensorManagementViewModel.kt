package com.example.glucosereadings.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.glucosereadings.repositories.SensorRepository
import com.example.glucosereadings.utils.SensorStates
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers.io


class SensorManagementViewModel(private val repository: SensorRepository) : ViewModel() {

    val egv = repository.egv

    private lateinit var disposable: Disposable


    private val _sensorState = MutableLiveData(SensorStates.NOT_PRESENT)
    val sensorState: LiveData<SensorStates> get() = _sensorState

    private val _isAlertShowed = MutableLiveData(false)
    val isAlertShowed: LiveData<Boolean> get() = _isAlertShowed

    fun changeAlertVisibility(isAlertShowed: Boolean) {
        _isAlertShowed.value = isAlertShowed
    }

    fun addSensor() {
        disposable = repository.addSensor()
            .subscribeOn(io())
            .observeOn(mainThread())
            .subscribe {
                _sensorState.postValue(it)
            }
    }

    fun deleteSensor() {
        _sensorState.postValue(SensorStates.NOT_PRESENT)
        repository.deleteSensor()
        disposable.dispose()
    }

    fun setSensorLimit(limit: Int) {
        repository.setSensorLimit(limit)
    }

}