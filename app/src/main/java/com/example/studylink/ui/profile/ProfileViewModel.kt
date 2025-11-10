package com.example.studylink.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.google.firebase.Timestamp

data class UserProfile(
    val name: String = "",
    val username: String = "",
    val createdAt: Timestamp? = null,
    val createdItems: List<String> = emptyList()
)

class ProfileViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> = _userProfile.asStateFlow()

    private val _createdItems = MutableStateFlow<List<String>>(emptyList())
    val createdItems: StateFlow<List<String>> = _createdItems.asStateFlow()

    private var profileListener: ListenerRegistration? = null
    private var itemsListener: ListenerRegistration? = null

    init {
        loadProfile()
        loadCreatedItems()
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
                    val profile = document.toObject(UserProfile::class.java)
                    _userProfile.value = profile
                } else {
                    _userProfile.value = null
                }
            }
    }

    private fun loadCreatedItems() {
        val userId = auth.currentUser?.uid ?: return
        itemsListener?.remove()
        itemsListener = db.collection("notes").whereEqualTo("userId", userId)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (querySnapshot != null) {
                    val items = querySnapshot.documents.mapNotNull { it.getString("title") }
                    _createdItems.value = items
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
        itemsListener?.remove()
    }
}