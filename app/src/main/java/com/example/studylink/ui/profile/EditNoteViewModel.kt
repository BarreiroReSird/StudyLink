package com.example.studylink.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditNoteViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun getNote(noteId: String, callback: (Note?) -> Unit) {
        db.collection("notes").document(noteId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    val note = document.toObject(Note::class.java)?.copy(id = document.id)
                    callback(note)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    fun updateNote(
        noteId: String, 
        title: String, 
        content: String, 
        onSuccess: () -> Unit, 
        onError: (Exception) -> Unit
    ) {
        val userId = auth.currentUser?.uid
        if (userId == null) {
            onError(Exception("Utilizador não autenticado"))
            return
        }

        val noteRef = db.collection("notes").document(noteId)

        noteRef.get()
            .addOnSuccessListener { document ->
                val note = document.toObject(Note::class.java)
                if (note?.userId == userId) {
                    noteRef.update("title", title, "content", content)
                        .addOnSuccessListener { onSuccess() }
                        .addOnFailureListener { onError(it) }
                } else {
                    onError(Exception("Não tem permissão para editar esta nota"))
                }
            }
            .addOnFailureListener { onError(it) }
    }
}