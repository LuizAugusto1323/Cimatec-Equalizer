package com.example.cimatecequalizer.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Equalizer(
    val name: String = "Equalizer",
    val level: Int = 10,
    val frequencies: List<Float> = listOf(0f, 0f, 0f, 0f, 0f, 0f),
) : Parcelable
