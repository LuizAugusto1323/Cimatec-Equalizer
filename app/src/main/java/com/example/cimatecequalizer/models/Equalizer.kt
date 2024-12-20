package com.example.cimatecequalizer.models

data class Equalizer (
    val id: Int = 0,
    val name: String,
    val level: Int,
    val frequencies: List<Int>,
)
