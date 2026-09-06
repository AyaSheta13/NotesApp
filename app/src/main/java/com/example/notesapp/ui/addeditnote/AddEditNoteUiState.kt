package com.example.notesapp.ui.addeditnote

data class AddEditNoteUiState(
    val title: String = "",
    val content: String = "",
    val isLoading: Boolean = false,
    val titleError: String? = null,
    val isEditing: Boolean = false
)