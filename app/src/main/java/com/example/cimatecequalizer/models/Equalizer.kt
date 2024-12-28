package com.example.cimatecequalizer.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/*
    Data Class do Equalizador
    nome: nome do usuário
    level: nível do equalizador
    frequencies: frequência do equalizador
*/

@Serializable
@Parcelize
data class Equalizer(
    val name: String = "Equalizer",
    val level: Int = 10,
    val frequencies: List<Float> = listOf(0f, 0f, 0f, 0f, 0f, 0f),
) : Parcelable
