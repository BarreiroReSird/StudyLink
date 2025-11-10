package com.example.studylink.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.studylink.ui.profile.ProfileViewModel

@Composable
fun DashboardScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val viewModel: DashboardViewModel = viewModel()
    val userProfile by profileViewModel.userProfile.collectAsState()
    var showProfileMessage by remember { mutableStateOf(false) }


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dashboard",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = { navController.navigate("profile") }
        ) {
            Text("Perfil")
        }

        Button(
            modifier = Modifier.padding(8.dp),
            onClick = {
                if (userProfile != null) {
                    navController.navigate("notes")
                } else {
                    showProfileMessage = true
                }
            }
        ) {
            Text("Notas")
        }

        if (showProfileMessage) {
            Text(
                text = "Por favor, crie um perfil primeiro.",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(
            modifier = Modifier.padding(8.dp),
            onClick = {
                viewModel.logout()
                navController.navigate("login") {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            }
        ) {
            Text("Terminar Sessão")
        }
    }
}