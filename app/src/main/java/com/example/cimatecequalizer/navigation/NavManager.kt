package com.example.cimatecequalizer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cimatecequalizer.viewModels.UserViewModel
import com.example.cimatecequalizer.views.EqualizerView
import com.example.cimatecequalizer.views.UserView

@Composable
internal fun NavManager(
    userViewModel: UserViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "userView"
    ) {
        composable(route = "userView") {
            UserView(navController, userViewModel)
        }

        composable(route = "equalizerView") {
            EqualizerView(navController)
        }
    }
}
