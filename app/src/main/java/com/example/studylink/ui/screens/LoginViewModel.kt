package com.example.studylink.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private val _uiState = mutableStateOf(LoginUIState())
    val uiState: State<LoginUIState> = _uiState
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login(onLoginSuccess: () -> Unit) {
        // Obtem o email e a password do estado
        val email = _uiState.value.email
        val password = _uiState.value.password

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            _uiState.value = _uiState.value.copy(error = "Email and/or password cannot be left blank.")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        // Inicia a sessao com o email e a password no Firebase
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _uiState.value = _uiState.value.copy(isLoading = false)

                if (task.isSuccessful) {
                    onLoginSuccess()
                } else {
                    _uiState.value = _uiState.value.copy(error = task.exception?.message ?: "An error occurred during login.")
                }
            }
    }
}

data class LoginUIState(
    val email: String? = null,
    val password: String? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)