package com.example.studylink.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class UserProfile(
    val name: String = "",
    val username: String = ""
)

class ProfileViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    private var profileListener: ListenerRegistration? = null

    init {
        loadProfile()
    }

    private fun loadProfile() {
        val userId = auth.currentUser?.uid ?: return
        profileListener?.remove()
        profileListener = db.collection("users").document(userId)
            .addSnapshotListener { document, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (document != null && document.exists()) {
                    _userProfile.value = document.toObject(UserProfile::class.java)
                } else {
                    _userProfile.value = null
                }
            }
    }

    fun saveProfile(name: String, username: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val userId = auth.currentUser?.uid ?: return
        val userDocRef = db.collection("users").document(userId)

        if (_userProfile.value == null) {
            val userProfileData = hashMapOf(
                "name" to name,
                "username" to username,
                "createdAt" to FieldValue.serverTimestamp()
            )
            userDocRef.set(userProfileData)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener(onError)
        } else {
            val updatedData = mapOf(
                "name" to name,
                "username" to username
            )
            userDocRef.update(updatedData)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener(onError)
        }
    }

    override fun onCleared() {
        super.onCleared()
        profileListener?.remove()
    }
}