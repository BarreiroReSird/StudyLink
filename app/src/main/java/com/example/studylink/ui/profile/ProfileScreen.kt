package com.example.studylink.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController, profileViewModel: ProfileViewModel = viewModel()) {
    val userProfile by profileViewModel.userProfile.collectAsState()
    var isEditing by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val profile = userProfile
        if (profile == null) {
            ProfileForm(
                profileViewModel = profileViewModel,
                onSave = { }
            )
        } else {
            if (isEditing) {
                ProfileForm(
                    userProfile = profile,
                    profileViewModel = profileViewModel,
                    onSave = { isEditing = false },
                    onCancel = { isEditing = false }
                )
            } else {
                ProfileDisplayScreen(
                    userProfile = profile,
                    onEditClick = { isEditing = true },
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun ProfileDisplayScreen(userProfile: UserProfile, onEditClick: () -> Unit, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Nome: ${userProfile.name}", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Username: ${userProfile.username}", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = onBackClick) {
                Text("Voltar")
            }
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = onEditClick) {
                Text("Editar Perfil")
            }
        }
    }
}

@Composable
fun ProfileForm(
    userProfile: UserProfile? = null,
    profileViewModel: ProfileViewModel,
    onSave: () -> Unit,
    onCancel: (() -> Unit)? = null
) {
    var name by remember(userProfile) { mutableStateOf(userProfile?.name ?: "") }
    var username by remember(userProfile) { mutableStateOf(userProfile?.username ?: "") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val title = if (userProfile == null) "Criar o Seu Perfil" else "Editar o Seu Perfil"
        Text(text = title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    profileViewModel.saveProfile(name, username,
                        onSuccess = onSave,
                        onError = { e -> error = e.message }
                    )
                }) {
                Text("Guardar")
            }
            onCancel?.let {
                Button(
                    modifier = Modifier.padding(8.dp),
                    onClick = it) {
                    Text("Cancelar")
                }
            }
        }
        error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }
    }
}