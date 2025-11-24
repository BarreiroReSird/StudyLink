package com.example.studylink.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.studylink.ui.theme.StudyLinkTheme

// To do:
// Double password verification;
@Composable
fun RegisterView(
    navController: NavController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val viewModel: RegisterViewModel = viewModel()
    val uiState by viewModel.uiState

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {
        Text(
            text = "StudyLink",
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "Feito por Barreiro_ReSird"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Registo",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = uiState.email ?: "",
            label = { Text("Email") },
            modifier = Modifier.padding(8.dp),
            onValueChange = {
                viewModel.updateEmail(it)
            })

        TextField(
            value = uiState.password ?: "",
            label = { Text("Palavra-passe") },
            modifier = Modifier.padding(8.dp),
            visualTransformation = PasswordVisualTransformation(),
            onValueChange = {
                viewModel.updatePassword(it)
            })

        if (uiState.error != null) {
            Text(
                text = uiState.error!!,
                modifier = Modifier.padding(8.dp),
            )
        }

        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = {
                    viewModel.register() {
                        navController.navigate("login")
                    }
                }) {
                Text("Efetuar registo")
            }
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = { navController.navigate("login") }) {
                Text("Aba login")
            }
        }

        if (uiState.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterViewPreview() {
    StudyLinkTheme {
        RegisterView()
    }
}