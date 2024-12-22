package com.example.cimatecequalizer.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.cimatecequalizer.viewModels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UpdateUserView(
    navController: NavController,
    userViewModel: UserViewModel,
    id: Int,
    name: String,
    eqName: String,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Informações do Usuário",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.Black,
                            )
                        }
                    )
                }
            )
        },
    ) {
        UpdateContentView(it, navController, userViewModel, id, name, eqName)
    }
}

@Composable
fun UpdateContentView(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: UserViewModel,
    id: Int,
    name: String,
    eqName: String
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        content = {
            Text(
                text = "Atualizar Usuário",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "id = $id, name = $name, eqName = $eqName",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
    )
}
