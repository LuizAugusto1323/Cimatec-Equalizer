package com.example.cimatecequalizer.viewModels

import androidx.lifecycle.ViewModel

class UserViewModel() : ViewModel() {

    fun createUser() {
        println("Create user")
    }

    fun deleteUser() {
        println("Delete user")
    }

    fun updateUser() {
        println("Update user")
    }
}
