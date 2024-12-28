package com.example.cimatecequalizer.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cimatecequalizer.models.Equalizer
import com.example.cimatecequalizer.models.User
import com.example.cimatecequalizer.room.UserDao
import com.example.cimatecequalizer.states.EqualizerUiState
import com.example.cimatecequalizer.states.UserDetailUiState
import com.example.cimatecequalizer.states.UserListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(private val userDao: UserDao) : ViewModel() {
    // Variáveis observadas //
    private val _uiUserListState = MutableStateFlow(UserListUiState())
    val uiListViewState = _uiUserListState.asStateFlow()

    private val _uiUserDetailViewState = MutableStateFlow(UserDetailUiState())
    val uiUserDetailViewState = _uiUserDetailViewState.asStateFlow()

    private val _uiEqualizerViewState = MutableStateFlow(EqualizerUiState())
    val uiEqualizerViewState = _uiEqualizerViewState.asStateFlow()

    // todo: remover logs quando os testes unitarios estiverem implementados.
    init {
        viewModelScope.launch {
            userDao.getUsers().collectLatest {
                println("********** state $it")
                _uiUserListState.update { currenState ->
                    currenState.copy(userList = it)
                }
            }
        }
    }

    // Insere um novo usuário //
    fun createUser(userName: String, eqName: String) {
        val user = User(name = userName, equalizer = Equalizer(name = eqName))
        viewModelScope.launch {
            println("********** create user $user")
            userDao.insertUser(user = user)
        }
    }

    // Deleta um usuário //
    fun deleteUser(user: User) {
        viewModelScope.launch {
            println("********** delete user $user")
            userDao.deleteUser(user = user)
        }
    }

    // Atualiza um usuário //
    fun updateUser(userId: Int, userName: String, eqName: String) {
        viewModelScope.launch {
            val user = userDao.getUser(userId).copy(name = userName).let {
                it.copy(equalizer = it.equalizer.copy(name = eqName))
            }
            println("********** update user")
            userDao.updateUser(user = user)
        }
    }

    // Salva as informações do equalizador //
    fun updateEqualizer(userId: Int, column: Int, frequency: Float) {
        viewModelScope.launch {
            val user = userDao.getUser(userId).let {
                it.copy(
                    equalizer = it.equalizer.copy(
                        frequencies = it.equalizer.frequencies.toMutableList()
                            .apply { this[column] = frequency })
                )
            }

            println("********** update Equalizer $user")
            userDao.updateUser(user = user)
        }
    }
}
