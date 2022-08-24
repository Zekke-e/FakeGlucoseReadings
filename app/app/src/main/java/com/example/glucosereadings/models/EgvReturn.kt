package com.example.glucosereadings.models

import com.example.glucosereadings.utils.EgvStates

data class EgvReturn(
    var egvStates: EgvStates? = null,
    var egvValue: Int? = null,
) {
    init {
        egvStates = when (egvValue) {
            in -5..40 -> EgvStates.CRITICAL
            null -> EgvStates.DEFAULT
            else -> EgvStates.NORMAL
        }
    }
}
