package com.example.cimatecequalizer.states

import com.example.cimatecequalizer.models.User

data class UserState(
    val userList: List<User> = emptyList()
)
