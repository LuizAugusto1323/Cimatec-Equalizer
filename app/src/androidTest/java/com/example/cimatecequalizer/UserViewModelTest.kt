package com.example.cimatecequalizer

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cimatecequalizer.room.UserDao
import com.example.cimatecequalizer.room.UserDataBase
import com.example.cimatecequalizer.viewModels.UserViewModel
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserViewModelTest {

    private lateinit var database: UserDataBase
    private lateinit var dao: UserDao
    private lateinit var viewModel: UserViewModel

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, UserDataBase::class.java)
//            .allowMainThreadQueries()
            .build()
        dao = database.userDao()
        viewModel = UserViewModel(dao)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun create_user_and_check_if_it_was_inserted() {
        viewModel.createUser("Francis", "Room")
        assertEquals("Francis", dao.findUsersByName("Francis").name)
        assertEquals("Room", dao.findUsersByName("Francis").equalizer.name)
    }

    @Test
    fun delete_user_and_check_if_it_was_deleted() {
        viewModel.createUser("Francis", "Room")
        viewModel.deleteUser(dao.findUsersByName("Francis"))
        assertEquals(null, dao.findUsersByName("Francis"))
    }

    @Test
    fun update_user_and_check_if_it_was_updated() {
        viewModel.createUser("Francis", "Room")
        val user = dao.findUsersByName("Francis")
        viewModel.updateUser(user.id, "Francis Mariano", "Room 2")
        assertEquals("Francis Mariano", dao.findUsersByName("Francis Mariano").name)
        assertEquals("Room 2", dao.findUsersByName("Francis Mariano").equalizer.name)
    }

    @Test
    fun update_equalizer_and_check_if_it_was_updated() {
        viewModel.createUser("Francis", "Room")
        val user = dao.findUsersByName("Francis")
        viewModel.updateEqualizer(user.id, 2, 5f)
        assertEquals(listOf(0f, 0f, 5f, 0f, 0f, 0f), dao.findUsersByName("Francis").equalizer.frequencies)
    }

}