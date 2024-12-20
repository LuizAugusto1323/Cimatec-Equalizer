package com.example.cimatecequalizer.models

data class User (
    val id: Int = 0,
    val name: String,
    val equalizers: List<Equalizer>,
)
