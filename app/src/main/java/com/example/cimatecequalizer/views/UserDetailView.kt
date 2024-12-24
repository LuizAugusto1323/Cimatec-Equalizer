package com.example.cimatecequalizer.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    userId: Int,
    userName: String,
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
        UpdateContentView(it, navController, userViewModel, userId, userName, eqName)
    }
}

@Composable
fun UpdateContentView(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: UserViewModel,
    userId: Int,
    userName: String,
    eqName: String,
) {

    var userNameView by remember { mutableStateOf(userName) }
    var eqNameView by remember { mutableStateOf(eqName) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
        content = {
            Text(
                text = "ID = $userId",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Name",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = userNameView,
                onValueChange = { userNameView = it },
                //label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "EqName",
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
            OutlinedTextField(
                value = eqNameView,
                onValueChange = { eqNameView = it },
                //label = { Text("Nome") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = {
                viewModel.updateUser(userId, userNameView, eqNameView)
            }) {
                Text("Update")
            }
        }
    )
}
