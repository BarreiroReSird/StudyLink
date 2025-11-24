package com.example.studylink

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.studylink.ui.theme.Note
import com.example.studylink.ui.theme.StudyLinkTheme

@Composable
fun NoteList(notes: List<Note>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(notes) { note ->
            NoteItem(note = note, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun NoteItem(note: Note, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = note.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteListPreview() {
    StudyLinkTheme {
        NoteList(notes = listOf(
            Note(title = "Nota 1", content = "Conteúdo da nota 1"),
            Note(title = "Nota 2", content = "Conteúdo da nota 2")
        ))
    }
}
