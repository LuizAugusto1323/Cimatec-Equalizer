package com.example.cimatecequalizer.navigation

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.example.cimatecequalizer.models.Equalizer
import kotlinx.serialization.json.Json

object CustomNavType {

    val EqualizerNavType = object : NavType<Equalizer>(
        isNullableAllowed = false
    ) {
        override fun get(bundle: Bundle, key: String): Equalizer? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                bundle.getParcelable(key, Equalizer::class.java)
            } else {
                @Suppress("DEPRECATION")
                bundle.getParcelable(key)
            }
        }

        override fun parseValue(value: String): Equalizer {
            return Json.decodeFromString<Equalizer>(value)
        }

        override fun put(bundle: Bundle, key: String, value: Equalizer) {
            bundle.putParcelable(key, value)
        }

        override fun serializeAsValue(value: Equalizer): String {
            return Json.encodeToString(value)
        }
    }
}
