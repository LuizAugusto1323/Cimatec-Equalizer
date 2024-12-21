package com.example.cimatecequalizer.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cimatecequalizer.models.User
import com.example.cimatecequalizer.room.converters.Converters

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class UserDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
