package com.example.studylink.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun ProfileScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel: ProfileViewModel = viewModel()
    val uiState by viewModel.uiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Editar Perfil",
            style = MaterialTheme.typography.headlineMedium
        )

        TextField(
            value = uiState.name,
            onValueChange = { viewModel.updateName(it) },
            label = { Text("Nome") },
            modifier = Modifier.padding(8.dp)
        )

        TextField(
            value = uiState.username,
            onValueChange = { viewModel.updateUsername(it) },
            label = { Text("Username") },
            modifier = Modifier.padding(8.dp)
        )

        if (uiState.error != null) {
            Text(
                text = uiState.error!!,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp),
            )
        }

        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = { viewModel.saveProfile { navController.popBackStack() } }
            ) {
                Text("Guardar")
            }

            Button(
                modifier = Modifier.padding(8.dp),
                onClick = { navController.popBackStack() }
            ) {
                Text("Voltar")
            }
        }
        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
    }
}