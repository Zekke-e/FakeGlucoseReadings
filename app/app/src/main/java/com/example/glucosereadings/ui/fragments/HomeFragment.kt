package com.example.glucosereadings.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.glucosereadings.R
import com.example.glucosereadings.databinding.FragmentHomeBinding
import com.example.glucosereadings.databinding.SensorAlertDialogBinding
import com.example.glucosereadings.repositories.SensorRepository
import com.example.glucosereadings.utils.EgvStates
import com.example.glucosereadings.utils.SensorStates
import com.example.glucosereadings.viewmodels.SensorManagementViewModel
import com.example.glucosereadings.viewmodels.SensorManagementViewModelFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding get() = _binding!!

    val sensorManagementViewModel by activityViewModels<SensorManagementViewModel> {
        SensorManagementViewModelFactory(SensorRepository.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeCgmState()
        observeEgvValue()
    }

    private fun observeCgmState() {
        sensorManagementViewModel.sensorState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SensorStates.NOT_PRESENT -> {
                    showNoSensor()
                }
                SensorStates.PRESENT -> {
                    showSensorPresented()
                }
            }
        }
    }

    private fun observeEgvValue() {
        sensorManagementViewModel.egv.observe(viewLifecycleOwner) { egv ->
            when (egv?.egvStates) {
                EgvStates.CRITICAL -> {
                    binding.tvHomeEgvValue.text = egv.egvValue.toString()
                    if (!sensorManagementViewModel.isAlertShowed.value!!)
                        showSensorAddedAlert()
                }
                EgvStates.DEFAULT -> {
                    binding.tvHomeEgvValue.text = "---"
                }
                EgvStates.NORMAL -> {
                    binding.tvHomeEgvValue.text = egv.egvValue.toString()
                    sensorManagementViewModel.changeAlertVisibility(false)
                }
                else -> Unit
            }
        }
    }

    private fun showNoSensor() {
        binding.tvHomeSensorStatusValue.text = "NO SENSOR"
        binding.tvHomeEgvHint.visibility = View.VISIBLE
    }

    private fun showSensorPresented() {
        binding.tvHomeSensorStatusValue.text = "SENSOR PRESENTED"
        binding.tvHomeEgvHint.visibility = View.GONE
    }

    private fun showSensorAddedAlert() {
        sensorManagementViewModel.changeAlertVisibility(true)
        val dialogView = SensorAlertDialogBinding.inflate(LayoutInflater.from(context))
        val dialog = AlertDialog.Builder(context).create()

        with(dialogView) {
            tvSensorAlertDialogTitle.text = "ALERT"
            tvSensorAlertDialogMessage.text = "Critical insulin level!"
            imageView.setImageResource(R.drawable.ic_remove)
            btnSensorAlertDialogPositive.setOnClickListener {
                dialog.dismiss()
            }
        }

        with(dialog) {
            setCancelable(false)
            setView(dialogView.root)
            show()
        }
    }

}