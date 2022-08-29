package com.example.glucosereadings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.glucosereadings.models.EgvReturn
import com.example.glucosereadings.repositories.SensorRepository
import com.example.glucosereadings.utils.EgvStates
import com.example.glucosereadings.utils.SensorStates
import com.example.glucosereadings.viewmodels.SensorManagementViewModel
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.spyk
import io.reactivex.internal.operators.observable.ObservableFromCallable
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.lang.Thread.sleep

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private lateinit var mockRepository: SensorRepository
    private lateinit var viewModel: SensorManagementViewModel

    @Before
    fun setUp() {
        mockRepository = mockk<SensorRepository>()
        viewModel = SensorManagementViewModel(mockRepository)
        val x = createMockedSensorStateField()
        x.postValue(EgvReturn(null,45))
        every { mockRepository.addSensor() } answers {
            ObservableFromCallable { SensorStates.PRESENT }
        }
        every { mockRepository.egv.value?.egvValue }.returns (45)

    }

    @Test
    fun egv_states() {
        val egvEqualCritical = spyk<EgvReturn>().copy(egvStates = EgvStates.CRITICAL, egvValue = 40)
        val egvBelowCritical = spyk<EgvReturn>().copy(egvStates = EgvStates.CRITICAL, egvValue = 20)
        val egvNormal = spyk<EgvReturn>().copy(egvStates = EgvStates.NORMAL, egvValue = 41)
        val egvDefault = spyk<EgvReturn>().copy(egvStates = EgvStates.DEFAULT, egvValue = null)

        assertEquals(EgvReturn(egvStates = EgvStates.NORMAL, egvValue = 41), egvNormal)
        assertEquals(EgvReturn(egvStates = EgvStates.CRITICAL, egvValue = 40), egvEqualCritical)
        assertEquals(EgvReturn(egvStates = EgvStates.CRITICAL, egvValue = 20), egvBelowCritical)
        assertEquals(EgvReturn(egvStates = EgvStates.DEFAULT, egvValue = null), egvDefault)
    }


    @Test
    fun testAddSensor() {
        viewModel.addSensor()
        sleep(1000)
        assertEquals(SensorStates.PRESENT, viewModel.sensorState.value)
    }


    private fun createMockedSensorStateField(): MutableLiveData<EgvReturn?> {
        val mockedSensorStateField =
            mockRepository.javaClass.getDeclaredField("_egv").apply {
                this.isAccessible = true
            }
        return mockedSensorStateField.get(mockRepository) as MutableLiveData<EgvReturn?>
    }
}