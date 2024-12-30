package com.example.cimatecequalizer.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.cimatecequalizer.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    // Pega as informações de todos os usuários //
    @Query("SELECT * FROM users")
    fun getUsers(): Flow<List<User>>

    // Pega um usuário em específico //
    @Query("SELECT * FROM users WHERE id == :userId")
    suspend fun getUser(userId: Int): User

    // Pega um usuário em específico através de seu nome //
    @Query("SELECT * FROM users WHERE name == :name")
    suspend fun findUsersByName(name: String): User

    // Insere um novo usuário //
    @Insert
    suspend fun insertUser(user: User)

    // Deleta um usuário //
    @Delete
    suspend fun deleteUser(user: User)

    // Atualiza um usuário //
    @Update
    suspend fun updateUser(user: User)
}
