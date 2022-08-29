package com.example.glucosereadings

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.glucosereadings.models.EgvReturn
import com.example.glucosereadings.repositories.SensorRepository
import com.example.glucosereadings.ui.fragments.HomeFragment
import com.example.glucosereadings.utils.SensorStates
import com.example.glucosereadings.viewmodels.SensorManagementViewModel
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Thread.sleep


@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {

    private lateinit var scenario: FragmentScenario<HomeFragment>
    private lateinit var viewModel: SensorManagementViewModel

    private lateinit var repository: SensorRepository


    @Before
    fun setUp() {
        scenario = launchFragmentInContainer()
        repository = mockk<SensorRepository>(relaxed = true)
        scenario.withFragment {
            viewModel = spyk(this.sensorManagementViewModel, recordPrivateCalls = true)
        }

        every {
            viewModel.egv.value?.egvValue
        } answers {
            60
        }
        every {
            viewModel.addSensor()
        } answers {
            viewModel._sensorState.postValue(SensorStates.PRESENT)
            viewModel.egv.value?.egvValue

        }

    }

    @Test
    fun checkHomeScreen() {
        onView(withId(R.id.tv_Home_SensorStateLabel)).check(matches(withText("Sensor status:")))
        onView(withId(R.id.tv_Home_SensorStatusValue)).check(matches(withText("NO SENSOR")))
        onView(withId(R.id.tv_Home_EgvValue)).check(matches(withText("---")))
        onView(withId(R.id.tv_Home_EgvHint)).check(matches(withText("EGV will be visible after CGM installation")))
    }

    @Test
    fun checkEgvValue() {
        viewModel.addSensor()
        println("mocki" + viewModel.egv.value?.egvValue)
        viewModel.egv.value?.egvValue

        sleep(10000)

        onView(withId(R.id.tv_Home_SensorStatusValue)).check(matches(withText("SENSOR PRESENTED")))
        onView(withId(R.id.tv_Home_EgvValue)).check(matches(not(withText("---"))))
    }
}