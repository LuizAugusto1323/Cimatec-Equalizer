package com.example.cimatecequalizer.states

import com.example.cimatecequalizer.models.Equalizer

data class EqualizerUiState(
    val userId: Int = -1,
    val userName: String = "",
    val equalizer: Equalizer = Equalizer(),
)
