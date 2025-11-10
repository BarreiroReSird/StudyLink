package com.example.studylink.ui.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NoteDetailScreen(
    navController: NavController,
    noteId: String,
    viewModel: NoteDetailViewModel = viewModel()
) {
    LaunchedEffect(noteId) {
        viewModel.loadNote(noteId)
    }

    val note by viewModel.note.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(onClick = { navController.popBackStack() }) {
            Text("Voltar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        note?.let {
            Text(text = it.title, style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Autor: ${it.authorUsername}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(4.dp))
            it.createdAt?.toDate()?.let {
                Text(
                    text = "Criado em: ${SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(it)}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = it.content, style = MaterialTheme.typography.bodyMedium)
        } ?: run {
            Text("Nota não encontrada ou a carregar...")
        }
    }
}