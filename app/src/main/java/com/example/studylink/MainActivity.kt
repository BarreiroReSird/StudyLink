package com.example.studylink

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studylink.ui.screens.LoginView
import com.example.studylink.ui.screens.NoteListScreen
import com.example.studylink.ui.screens.RegisterView
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
                StudyLinkApp()
            }
        }
    }
}

@Composable
fun StudyLinkApp() {
    // Cria um NavController para gerir a navegacao
    val navController = rememberNavController()

    // O NavHost e um container para os diferentes ecras da app
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginView(navController = navController)
        }
        composable("register") {
            RegisterView(navController = navController)
        }
        composable("home") {
            NoteListScreen()
        }
    }
}