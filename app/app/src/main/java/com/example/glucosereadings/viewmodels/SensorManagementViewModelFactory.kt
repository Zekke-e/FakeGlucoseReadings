package com.example.glucosereadings.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.glucosereadings.repositories.SensorRepository

class SensorManagementViewModelFactory constructor(
    private val repository: SensorRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SensorManagementViewModel::class.java) -> {
                SensorManagementViewModel(repository) as T
            }
            else -> throw IllegalArgumentException("Error")
        }
    }
}