package com.example.studylink.ui.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ProfileView(navController: NavController, profileViewModel: ProfileViewModel = viewModel()) {
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
                ProfileDisplayView(
                    userProfile = profile,
                    onEditClick = { isEditing = true },
                    onBackClick = { navController.popBackStack() }
                )
            }
        }
    }
}

@Composable
fun ProfileDisplayView(userProfile: UserProfile, onEditClick: () -> Unit, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Nome: ${userProfile.name}", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Username: ${userProfile.username}", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier.width(140.dp),
                onClick = onBackClick) {
                Text("Voltar")
            }
            Button(
                modifier = Modifier.width(140.dp),
                onClick = onEditClick) {
                Text("Editar perfil")
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

        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier.padding(8.dp).width(280.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.padding(8.dp).width(280.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                modifier = Modifier.width(140.dp),
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
                    modifier = Modifier.width(140.dp),
                    onClick = it) {
                    Text("Cancelar")
                }
            }
        }
        error?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}