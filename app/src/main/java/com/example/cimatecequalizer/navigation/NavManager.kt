package com.example.cimatecequalizer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cimatecequalizer.viewModels.UserViewModel
import com.example.cimatecequalizer.views.EqualizerView
import com.example.cimatecequalizer.views.UpdateUserView
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

        composable(
            route = "updateUserView/{id}/{name}/{eqName}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("name") { type = NavType.StringType },
                navArgument("eqName") { type = NavType.StringType },
            )
        ) {
            UpdateUserView(
                navController = navController,
                userViewModel = userViewModel,
                id = it.arguments?.getInt("id") ?: 0,
                name = it.arguments?.getString("name") ?: "",
                eqName = it.arguments?.getString("eqName") ?: "",
            )
        }

        composable(
            route = "equalizerView/{id}/{name}/{eqName}",
            arguments = listOf(
                navArgument("id") { type = NavType.IntType },
                navArgument("name") { type = NavType.StringType },
                navArgument("eqName") { type = NavType.StringType },
            )
        ) {
            EqualizerView(
                navController = navController,
                userViewModel = userViewModel,
                id = it.arguments?.getInt("id") ?: 0,
                name = it.arguments?.getString("name") ?: "",
                eqName = it.arguments?.getString("eqName") ?: "",
            )
        }
    }
}
