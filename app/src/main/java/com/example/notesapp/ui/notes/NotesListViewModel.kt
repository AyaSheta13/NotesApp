package com.example.notesapp.ui.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notesapp.NotesApplication
import com.example.notesapp.data.local.NoteEntity
import com.example.notesapp.data.repository.NotesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NotesListViewModel(
    private val repository: NotesRepository
) : ViewModel() {

    val uiState: StateFlow<NotesListUiState> = repository.getAllNotes()
        .map { notes ->
            if (notes.isEmpty()) NotesListUiState.Empty
            else NotesListUiState.Success(notes)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = NotesListUiState.Loading
        )

    fun deleteNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as NotesApplication
                NotesListViewModel(app.repository)
            }
        }
    }
}
