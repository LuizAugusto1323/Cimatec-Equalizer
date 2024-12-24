package com.example.cimatecequalizer.states

import com.example.cimatecequalizer.models.User

data class UserListUiState(
    val userList: List<User> = emptyList()
)
