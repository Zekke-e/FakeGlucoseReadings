package com.example.glucosereadings.ui.fragments

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.glucosereadings.viewmodels.SensorManagementViewModel
import com.example.glucosereadings.viewmodels.SensorManagementViewModelFactory
import com.example.glucosereadings.repositories.SensorRepository
import com.example.glucosereadings.databinding.FragmentAddCgmBinding
import com.example.glucosereadings.databinding.SensorAlertDialogBinding

class AddCgmFragment : Fragment() {

    private var _binding: FragmentAddCgmBinding? = null
    private val binding: FragmentAddCgmBinding get() = _binding!!

    private val sensorManagementViewModel by activityViewModels<SensorManagementViewModel> {
        SensorManagementViewModelFactory(SensorRepository.getInstance())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCgmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.ivAddCgmCgmIcon.setOnLongClickListener {
            sensorManagementViewModel.addSensor()
            showSensorAdded()
            true
        }
    }

    private fun showSensorAdded() {
        val dialogView = SensorAlertDialogBinding.inflate(LayoutInflater.from(context))
        val dialog = AlertDialog.Builder(context).create()

        with(dialogView) {
            tvSensorAlertDialogTitle.text = "Add Sensor"
            tvSensorAlertDialogMessage.text = "Sensor has been added"
            btnSensorAlertDialogPositive.setOnClickListener {
                dialog.dismiss()
                findNavController().popBackStack()
            }
        }

        with(dialog) {
            setCancelable(false)
            setView(dialogView.root)
            show()
        }
    }

}