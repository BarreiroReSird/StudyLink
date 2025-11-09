package com.example.studylink.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    private val _uiState = mutableStateOf(LoginUIState())
    val uiState: State<LoginUIState> = _uiState

    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email)
    }

    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password)
    }

    fun login(onLoginSuccess: () -> Unit) {
        // TODO: Implement login logic
        onLoginSuccess()
    }
}

data class LoginUIState(
    val email: String? = null,
    val password: String? = null,
    val error: String? = null,
    val isLoading: Boolean = false
)