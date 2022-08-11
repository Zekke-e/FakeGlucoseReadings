package com.example.glucosereadings.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.activityViewModels
import com.example.glucosereadings.databinding.FragmentCgmSimulatorBinding
import com.example.glucosereadings.repositories.SensorRepository
import com.example.glucosereadings.utils.SensorStates
import com.example.glucosereadings.viewmodels.SensorManagementViewModel
import com.example.glucosereadings.viewmodels.SensorManagementViewModelFactory

class CgmSimulatorFragment : Fragment() {

    private var _binding: FragmentCgmSimulatorBinding? = null
    private val binding: FragmentCgmSimulatorBinding get() = _binding!!

    private val sensorManagementViewModel by activityViewModels<SensorManagementViewModel> {
        SensorManagementViewModelFactory(SensorRepository.getInstance())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCgmSimulatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeSensorState()
        setupSeekBar()
        setupApplyButton()
    }

    private fun observeSensorState() {
        sensorManagementViewModel.sensorState.observe(viewLifecycleOwner) { state ->
            when (state) {
                SensorStates.NOT_PRESENT -> { hideSimulatorShowInfo() }
                SensorStates.PRESENT -> { showSimulatorHideInfo() }
            }
        }
    }

    private fun setupSeekBar() {
        binding.sbSimulator.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar?, p1: Int, p2: Boolean) {
                binding.tvSimulatorEgvValue.text = seek?.progress?.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                /* do nothing */
            }

            override fun onStopTrackingTouch(seek: SeekBar?) {
                /* do nothing */
            }
        })
    }

    private fun setupApplyButton() {
        binding.btnSimulatorApply.setOnClickListener {
            if (binding.tvSimulatorEgvValue.text != "---") {
                val newLimit = binding.tvSimulatorEgvValue.text.toString().toInt()
                sensorManagementViewModel.setSensorLimit(newLimit)
            }
        }
    }

    private fun hideSimulatorShowInfo() {
        binding.btnSimulatorApply.visibility = View.GONE
        binding.tvSimulatorEgvValue.visibility = View.GONE
        binding.tvSimulatorEgvLimitLabel.visibility = View.GONE
        binding.sbSimulator.visibility = View.GONE

        binding.tvSimulatorNoSensorLabel.visibility = View.VISIBLE
    }

    private fun showSimulatorHideInfo() {
        binding.btnSimulatorApply.visibility = View.VISIBLE
        binding.tvSimulatorEgvValue.visibility = View.VISIBLE
        binding.tvSimulatorEgvLimitLabel.visibility = View.VISIBLE
        binding.sbSimulator.visibility = View.VISIBLE

        binding.tvSimulatorNoSensorLabel.visibility = View.GONE
    }
}