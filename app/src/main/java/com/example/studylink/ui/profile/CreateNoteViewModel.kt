package com.example.studylink.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class CreateNoteViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun createNote(title: String, content: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            onError(Exception("Utilizador não autenticado"))
            return
        }

        val userDocRef = db.collection("users").document(userId)
        userDocRef.get()
            .addOnSuccessListener { documentSnapshot ->
                val username = if (documentSnapshot != null && documentSnapshot.exists()) {
                    documentSnapshot.getString("username") ?: ""
                } else {
                    ""
                }

                val noteData = hashMapOf(
                    "title" to title,
                    "content" to content,
                    "createdAt" to FieldValue.serverTimestamp(),
                    "userId" to userId,
                    "authorUsername" to username
                )

                db.collection("notes")
                    .add(noteData)
                    .addOnSuccessListener { onSuccess() }
                    .addOnFailureListener { onError(it) }
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }
}