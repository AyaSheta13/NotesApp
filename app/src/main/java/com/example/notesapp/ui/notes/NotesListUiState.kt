package com.example.notesapp.ui.notes

import com.example.notesapp.data.local.NoteEntity

sealed interface NotesListUiState {
    object Loading : NotesListUiState
    object Empty : NotesListUiState
    data class Success(val notes: List<NoteEntity>) : NotesListUiState
}