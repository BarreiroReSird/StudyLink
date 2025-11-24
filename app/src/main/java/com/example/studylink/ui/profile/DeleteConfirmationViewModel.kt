package com.example.studylink.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class DeleteConfirmationViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _note = MutableStateFlow<Note?>(null)
    val note: StateFlow<Note?> = _note.asStateFlow()

    fun loadNote(noteId: String) {
        if (noteId.isBlank()) return

        db.collection("notes").document(noteId)
            .get()
            .addOnSuccessListener { document ->
                if (document != null && document.exists()) {
                    val noteData = document.toObject(Note::class.java)
                    _note.value = noteData?.copy(id = document.id)
                } else {
                    _note.value = null
                }
            }
            .addOnFailureListener {
                _note.value = null
            }
    }

    fun deleteNote(noteId: String, onNoteDeleted: () -> Unit) {
        db.collection("notes").document(noteId)
            .delete()
            .addOnSuccessListener { onNoteDeleted() }
    }
}