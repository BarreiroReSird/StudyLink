package com.example.studylink.ui.theme

data class Note(
    val id: String = "",
    val title: String = "",
    val content: String = "",
    val authorId: String = "",
    val isPrivate: Boolean = false,
    val timestamp: Long = 0
)