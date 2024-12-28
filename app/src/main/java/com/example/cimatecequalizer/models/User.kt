package com.example.cimatecequalizer.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/*
    Data Class User
    id: nome do usuário
    name: nível do equalizador
    equalizer: data class {
        nome: nome do usuário
        level: nível do equalizador
        frequencies: frequência do equalizador
    }
*/
@Serializable
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "equalizers")
    val equalizer: Equalizer,
)
