package com.example.studylink.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MyNotesViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private var notesListener: ListenerRegistration? = null

    init {
        loadNotes()
    }

    private fun loadNotes() {
        val userId = auth.currentUser?.uid
        if (userId == null) return

        notesListener?.remove()
        notesListener = db.collection("notes")
            .whereEqualTo("userId", userId)
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    return@addSnapshotListener
                }

                if (querySnapshot != null) {
                    val notesList = querySnapshot.documents.mapNotNull { doc ->
                        val note = doc.toObject(Note::class.java)
                        note?.copy(id = doc.id)
                    }
                    _notes.value = notesList
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        notesListener?.remove()
    }
}