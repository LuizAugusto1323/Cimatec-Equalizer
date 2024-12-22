package com.example.cimatecequalizer.views

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.cimatecequalizer.models.Equalizer
import com.example.cimatecequalizer.models.User
import com.example.cimatecequalizer.viewModels.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun UserView(
    navController: NavController,
    userViewModel: UserViewModel,
) {
    var showCreateUserPopup by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Usuários",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                    )
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showCreateUserPopup = true },
                shape = CircleShape,
                containerColor = Color.Black
            ) {
                Text("+", fontSize = 24.sp, color = Color.White)
            }
        },
        content = { padding ->
            UserContent(
                navController = navController,
                paddingValues = padding,
                showCreateUserPopup = showCreateUserPopup,
                userViewModel = userViewModel,
                closePopup = { showCreateUserPopup = false },
            )
        }
    )
}

@Composable
internal fun UserContent(
    navController: NavController,
    paddingValues: PaddingValues,
    showCreateUserPopup: Boolean,
    userViewModel: UserViewModel,
    closePopup: () -> Unit,
) {
    val state = userViewModel.state

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.padding(paddingValues)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(all = 20.dp),
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxSize()
                    .background(Color(0xFFE0E0E0), RoundedCornerShape(16.dp))
            ) {
                if (state.userList.isEmpty()) {
                    item {
                        Text(
                            text = "Nenhum usuário cadastrado",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                } else {
                    items(state.userList) { user ->
                        UserRow(
                            user = user,
                            goToEqualizer = { navController.navigate(user) },
                            editUser = {
                                navController.navigate(
                                    "updateUserView/${user.id}/${user.name}/${user.eqName}"
                                )
                            },
                            deleteUser = { userViewModel.deleteUser(user) }
                        )
                    }
                }
            }
        }

        if (showCreateUserPopup) {
            CreateUserPopup(
                onDismissRequest = { closePopup() },
                create = { name, eqName ->
                    userViewModel.createUser(
                        user = User(
                            name = name,
                            eqName = eqName,
                            equalizer = Equalizer(name = eqName)
                        )
                    )
                    closePopup()
                },
            )
        }
    }
}

@Composable
internal fun UserRow(
    user: User,
    goToEqualizer: () -> Unit,
    editUser: () -> Unit,
    deleteUser: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .pointerInput(Unit) { detectTapGestures { goToEqualizer() } }
            ) {
                Text(
                    text = user.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = user.eqName,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Row {
                IconButton(
                    onClick = { editUser() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Info"
                    )
                }

                IconButton(
                    onClick = { deleteUser() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Black
                    )
                }
            }
        }

        HorizontalDivider(
            color = Color.Black,
            thickness = 1.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Composable
internal fun CreateUserPopup(
    onDismissRequest: () -> Unit,
    create: (String, String) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Criar usuário",
                    style = MaterialTheme.typography.headlineSmall
                )

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nome") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descrição") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismissRequest) {
                        Text("Cancelar")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(onClick = {
                        create(name, description)
                    }) {
                        Text("Criar")
                    }
                }
            }
        }
    }
}
