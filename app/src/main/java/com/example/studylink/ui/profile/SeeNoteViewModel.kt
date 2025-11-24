package com.example.studylink.ui.profile

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.example.studylink.ui.theme.Note

class SeeNoteViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    private var notesListener: ListenerRegistration? = null

    init {
        loadNotes()
    }

    private fun loadNotes() {
        notesListener?.remove()
        notesListener = db.collection("notes")
            .orderBy("createdAt", Query.Direction.DESCENDING)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    // Handle error, maybe expose it to the UI
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