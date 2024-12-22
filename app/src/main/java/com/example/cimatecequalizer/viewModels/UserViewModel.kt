package com.example.cimatecequalizer.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cimatecequalizer.models.User
import com.example.cimatecequalizer.room.UserDao
import com.example.cimatecequalizer.states.UserState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao) : ViewModel() {
    var state by mutableStateOf(UserState())
        private set

    // todo: remover logs quando os testes unitarios estiverem implementados.
    init {
        viewModelScope.launch {
            userDao.getUsers().collectLatest {
                state = state.copy(userList = it)
                println("********** state $state")
            }
        }
    }

    fun createUser(user: User) {
        viewModelScope.launch {
            println("********** create user $user")
            userDao.insertUser(user = user)
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            println("********** delete user $user")
            userDao.deleteUser(user = user)
        }
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            println("********** update user $user")
            userDao.updateUser(user = user)
        }
    }
}
