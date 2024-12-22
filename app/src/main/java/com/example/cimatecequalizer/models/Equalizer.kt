package com.example.cimatecequalizer.models

import kotlinx.serialization.Serializable

@Serializable
data class Equalizer(
    val name: String = "Equalizer",
    val level: Int = 10,
    val frequencies: List<Int> = listOf(0, 0, 0, 0, 0, 0),
)
