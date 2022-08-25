package com.example.glucosereadings.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.glucosereadings.models.Cgm
import com.example.glucosereadings.models.EgvReturn
import com.example.glucosereadings.utils.SensorStates
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers.io

class SensorRepository private constructor() {

    private var cgm: Cgm? = null
    private var egvDisposable: Disposable? = null

    private val _egv = MutableLiveData<EgvReturn?>()
    val egv: LiveData<EgvReturn?> get() = _egv

    companion object {
        private var instance: SensorRepository? = null

        fun getInstance(): SensorRepository = synchronized(this) {
            if (instance == null) {
                instance = SensorRepository()
            }
            instance!!
        }
    }

    fun addSensor(): Observable<SensorStates> {
        if (cgm == null) {
            cgm = Cgm().also { sensor ->
                egvDisposable = sensor.getEgvObservable()
                    .subscribeOn(io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        _egv.value = EgvReturn(egvValue = it)
                    }
            }
        }
        return Observable.create<SensorStates> {
            it.onNext(SensorStates.PRESENT)
        }
    }


    fun deleteSensor() {
        if (cgm != null) {
            egvDisposable?.dispose()
            cgm = null
            _egv.value = EgvReturn()
        }
    }

    fun setSensorLimit(limit: Int) {
        cgm?.setEgvLimit(limit)
    }

}