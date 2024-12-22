package com.example.cimatecequalizer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.toRoute
import com.example.cimatecequalizer.models.Equalizer
import com.example.cimatecequalizer.models.User
import com.example.cimatecequalizer.viewModels.UserViewModel
import com.example.cimatecequalizer.views.EqualizerView
import com.example.cimatecequalizer.views.UpdateUserView
import com.example.cimatecequalizer.views.UserView
import kotlin.reflect.typeOf

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

        // navegação para informações do usuário
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

        // navegação para o equalizador do usuário
        composable<User>(
            typeMap = mapOf(typeOf<Equalizer>() to CustomNavType.EqualizerNavType)
        ) {
            val user = it.toRoute<User>()

            EqualizerView(
                navController = navController,
                userViewModel = userViewModel,
                user = user,
            )
        }
    }
}
