package com.example.studylink

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.studylink.ui.screens.DashboardScreen
import com.example.studylink.ui.screens.LoginView
import com.example.studylink.ui.screens.NoteListScreen
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
    // Cria um NavController para gerir a navegacao
    val navController = rememberNavController()

    // Obtem a instancia do FirebaseAuth para verificar o utilizador atual
    val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Verifica se ja existe um utilizador com sessao iniciada
    val startDestination = if (auth.currentUser != null) {
        // Se o utilizador estiver logado, o ecra inicial e o "home" (dashboard)
        "home"
    } else {
        // Se nao estiver logado, o ecra inicial e o "login"
        "login"
    }

    // O NavHost e um container para os diferentes ecras da app
    // O startDestination e definido dinamicamente com base no estado de login
    NavHost(navController = navController, startDestination = startDestination) {
        // Define o ecra de login
        composable("login") {
            LoginView(navController = navController)
        }
        // Define o ecra de registo
        composable("register") {
            RegisterView(navController = navController)
        }
        // Define o ecra de dashboard
        composable("home") {
            DashboardScreen(navController = navController)
        }
        // Define o ecra de perfil (temporario)
        composable("profile") {
            Text("Profile Screen")
        }
        // Define o ecra de anotacoes
        composable("notes") {
            NoteListScreen()
        }
    }
}