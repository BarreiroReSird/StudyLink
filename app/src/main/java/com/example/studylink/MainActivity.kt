package com.example.studylink

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.studylink.ui.screens.NoteListScreen
import com.example.studylink.ui.theme.StudyLinkTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        try {
            FirebaseApp.getInstance()
            Log.d("FirebaseTest", "Firebase is connected!")
        } catch (e: IllegalStateException) {
            Log.e("FirebaseTest", "Firebase is not connected.", e)
        }

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
