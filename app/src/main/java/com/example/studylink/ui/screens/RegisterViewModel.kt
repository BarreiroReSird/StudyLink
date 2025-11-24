package com.example.studylink.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class RegisterViewModel : ViewModel() {

    private val _uiState = mutableStateOf(RegisterUIState())
    val uiState: State<RegisterUIState> = _uiState

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.value = _uiState.value.copy(confirmPassword = confirmPassword)
    }

    fun register(onRegisterSuccess: () -> Unit) {
        val email = _uiState.value.email
        val password = _uiState.value.password
        val confirmPassword = _uiState.value.confirmPassword

        if (email.isNullOrEmpty() || password.isNullOrEmpty() || confirmPassword.isNullOrEmpty()) {
            _uiState.value = _uiState.value.copy(error = "Todos os campos são obrigatórios.")
            return
        }

        if (password != confirmPassword) {
            _uiState.value = _uiState.value.copy(error = "As palavras-passe não coincidem.")
            return
        }

        if (password.length < 6) {
            _uiState.value = _uiState.value.copy(error = "A palavra-passe deve ter pelo menos 6 caracteres.")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _uiState.value = _uiState.value.copy(isLoading = false)
                if (task.isSuccessful) {
                    onRegisterSuccess()
                } else {
                    // Mantém a mensagem de erro do Firebase (geralmente em inglês)
                    _uiState.value = _uiState.value.copy(error = task.exception?.message)
                }
            }
    }
}

data class RegisterUIState(
    val email: String? = null,
    val password: String? = null,
    val confirmPassword: String? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)