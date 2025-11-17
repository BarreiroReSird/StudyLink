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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun NoteMenuScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Menu de Notas",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = { navController.navigate("see_note") })
        {
            Text("Ver Notas")
        }
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = { navController.navigate("create_note") })
        {
            Text("Criar Nota")
        }
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = { navController.navigate("my_notes") })
        {
            Text("Editar Nota")
        }
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = { /* TODO: Navigate to delete note */ })
        {
            Text("Eliminar Nota")
        }
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = { navController.popBackStack() })
        {
            Text("Voltar")
        }
    }
}