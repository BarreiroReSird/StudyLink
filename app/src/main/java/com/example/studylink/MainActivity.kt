package com.example.studylink

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.studylink.ui.screens.NoteListScreen
import com.example.studylink.ui.theme.StudyLinkTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            StudyLinkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NoteListScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
