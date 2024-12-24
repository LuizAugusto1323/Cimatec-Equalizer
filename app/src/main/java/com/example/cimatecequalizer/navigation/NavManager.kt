package com.example.cimatecequalizer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.cimatecequalizer.models.Equalizer
import com.example.cimatecequalizer.models.User
import com.example.cimatecequalizer.viewModels.UserViewModel
import com.example.cimatecequalizer.views.EqualizerView
import com.example.cimatecequalizer.views.UpdateUserView
import com.example.cimatecequalizer.views.UserListView
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class UserDetailProfile(
    val userId: Int,
    val userName: String,
    val eqName: String,
)

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
            UserListView(navController, userViewModel)
        }

        // navegação para informações do usuário
        composable<UserDetailProfile> {
            val profile = it.toRoute<UserDetailProfile>()
            UpdateUserView(
                navController = navController,
                userViewModel = userViewModel,
                userId = profile.userId,
                userName = profile.userName,
                eqName = profile.eqName,
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
