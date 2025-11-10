package com.example.studylink

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.studylink.ui.profile.CreateNoteScreen
import com.example.studylink.ui.profile.NoteDetailScreen
import com.example.studylink.ui.profile.SeeNoteScreen
import com.example.studylink.ui.screens.DashboardScreen
import com.example.studylink.ui.screens.LoginView
import com.example.studylink.ui.screens.NoteMenuScreen
import com.example.studylink.ui.profile.ProfileScreen
import com.example.studylink.ui.screens.RegisterView
import com.example.studylink.ui.theme.StudyLinkTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

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
    val navController = rememberNavController()
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val startDestination = if (auth.currentUser != null) {
        "home"
    } else {
        "login"
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("login") {
            LoginView(navController = navController)
        }
        composable("register") {
            RegisterView(navController = navController)
        }
        composable("home") {
            DashboardScreen(navController = navController)
        }
        composable("profile") {
            ProfileScreen(navController = navController)
        }
        composable("notes") {
            NoteMenuScreen(navController = navController)
        }
        composable("create_note") {
            CreateNoteScreen(
                onNoteCreated = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }
        composable("see_note") {
            SeeNoteScreen(navController = navController)
        }
        composable(
            "note_detail/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            if (noteId != null) {
                NoteDetailScreen(navController = navController, noteId = noteId)
            }
        }
    }
}