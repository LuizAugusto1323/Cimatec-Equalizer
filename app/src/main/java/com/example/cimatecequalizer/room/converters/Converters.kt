package com.example.cimatecequalizer.room.converters

import androidx.room.TypeConverter
import com.example.cimatecequalizer.models.Equalizer
import kotlinx.serialization.json.Json

class Converters {
    @TypeConverter
    fun converterToEqualizer(equalizer: Equalizer): String {
        return Json.encodeToString(value = equalizer)
    }

    @TypeConverter
    fun converterFromEqualizer(value: String): Equalizer {
        return Json.decodeFromString(value)
    }
}
