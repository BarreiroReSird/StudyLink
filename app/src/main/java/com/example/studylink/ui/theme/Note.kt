package com.example.studylink.ui.theme

import com.google.firebase.Timestamp

data class Note(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val userId: String = "",
    val authorUsername: String = "",
    val isPrivate: Boolean = false,
    val timestamp: Long = 0,
    val createdAt: Timestamp? = null
)