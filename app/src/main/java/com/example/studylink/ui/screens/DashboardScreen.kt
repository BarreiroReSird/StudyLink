package com.example.studylink.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun DashboardScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val viewModel: DashboardViewModel = viewModel()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = { navController.navigate("profile") }
        ) {
            Text("Perfil")
        }

        Button(
            modifier = Modifier.padding(8.dp),
            onClick = { navController.navigate("notes") }
        ) {
            Text("Notas")
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