package com.example.studylink.ui.screens

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

data class ProfileUiState(
    val name: String = "",
    val username: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)

class ProfileViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val userId = auth.currentUser?.uid

    private val _uiState = mutableStateOf(ProfileUiState(isLoading = true))
    val uiState: State<ProfileUiState> = _uiState

    init {
        fetchProfile()
    }

    private fun fetchProfile() {
        if (userId != null) {
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        _uiState.value = ProfileUiState(
                            name = document.getString("name") ?: "",
                            username = document.getString("username") ?: "",
                            isLoading = false
                        )
                    } else {
                        _uiState.value = _uiState.value.copy(isLoading = false)
                    }
                }
                .addOnFailureListener {
                    _uiState.value = _uiState.value.copy(error = it.message, isLoading = false)
                }
        } else {
            _uiState.value = ProfileUiState(error = "Utilizador não autenticado.", isLoading = false)
        }
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(name = name, error = null)
    }

    fun updateUsername(username: String) {
        _uiState.value = _uiState.value.copy(username = username, error = null)
    }

    fun saveProfile(onSuccess: () -> Unit) {
        if (userId == null) {
            _uiState.value = _uiState.value.copy(error = "Utilizador não autenticado.")
            return
        }

        val name = _uiState.value.name
        val username = _uiState.value.username

        if (name.isBlank() || username.isBlank()) {
            _uiState.value = _uiState.value.copy(error = "Nome e Username são obrigatórios.")
            return
        }

        _uiState.value = _uiState.value.copy(isLoading = true, error = null)

        val userProfile = hashMapOf(
            "name" to name,
            "username" to username
        )

        db.collection("users").document(userId)
            .set(userProfile)
            .addOnSuccessListener {
                _uiState.value = _uiState.value.copy(isLoading = false)
                onSuccess()
            }
            .addOnFailureListener {
                _uiState.value = _uiState.value.copy(isLoading = false, error = it.message)
            }
    }
}