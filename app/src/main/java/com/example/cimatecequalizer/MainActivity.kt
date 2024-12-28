package com.example.cimatecequalizer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.example.cimatecequalizer.navigation.NavManager
import com.example.cimatecequalizer.room.UserDataBase
import com.example.cimatecequalizer.ui.theme.CimatecEqualizerTheme
import com.example.cimatecequalizer.viewModels.UserViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Theme do app //
            CimatecEqualizerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // data base
                    val database = Room.databaseBuilder(
                        context = this,
                        klass = UserDataBase::class.java,
                        name = "db_users"
                    ).build()

                    // dao
                    val userDao = database.userDao()

                    // view model
                    val userViewModel = UserViewModel(userDao = userDao)

                    // navigation
                    NavManager(
                        userViewModel = userViewModel,
                    )
                }
            }
        }
    }
}
