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
import com.example.studylink.ui.profile.CreateNoteView
import com.example.studylink.ui.profile.DeleteConfirmationView
import com.example.studylink.ui.profile.EditNoteView
import com.example.studylink.ui.profile.MyNotesView
import com.example.studylink.ui.profile.NoteDetailView
import com.example.studylink.ui.profile.SeeNoteScreen
import com.example.studylink.ui.profile.DeleteNotesView
import com.example.studylink.ui.screens.DashboardView
import com.example.studylink.ui.screens.LoginView
import com.example.studylink.ui.screens.NoteMenuView
import com.example.studylink.ui.profile.ProfileView
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
            DashboardView(navController = navController)
        }
        composable("profile") {
            ProfileView(navController = navController)
        }
        composable("notes") {
            NoteMenuView(navController = navController)
        }
        composable("see_note") {
            SeeNoteScreen(navController = navController)
        }
        composable("delete_note") {
            DeleteNotesView(navController = navController)
        }
        composable("create_note") {
            CreateNoteView(
                onNoteCreated = { navController.popBackStack() },
                onCancel = { navController.popBackStack() }
            )
        }
        composable("my_notes") {
            MyNotesView(navController = navController)
        }
        composable(
            "note_detail/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            if (noteId != null) {
                NoteDetailView(navController = navController, noteId = noteId)
            }
        }
        composable(
            "edit_note/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            if (noteId != null) {
                EditNoteView(
                    noteId = noteId,
                    onNoteUpdated = { navController.popBackStack() },
                    onCancel = { navController.popBackStack() }
                )
            }
        }
        composable(
            "delete_confirmation/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.StringType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")
            if (noteId != null) {
                DeleteConfirmationView(navController = navController, noteId = noteId)
            }
        }
    }
}