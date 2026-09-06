package com.example.notesapp.ui.addeditnote

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notesapp.NotesApplication
import com.example.notesapp.data.local.NoteEntity
import com.example.notesapp.data.repository.NotesRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddEditNoteViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: NotesRepository
) : ViewModel() {

    private val noteId: Int? = savedStateHandle.get<Int>("noteId")?.takeIf { it != -1 }

    private val _uiState = MutableStateFlow(AddEditNoteUiState())
    val uiState: StateFlow<AddEditNoteUiState> = _uiState.asStateFlow()

    private val _navigateBack = Channel<Unit>()
    val navigateBack: Flow<Unit> = _navigateBack.receiveAsFlow()

    init {
        if (noteId != null) {
            loadNote(noteId)
        }
    }

    private fun loadNote(id: Int) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val note = repository.getNoteById(id)
            if (note != null) {
                _uiState.update {
                    it.copy(title = note.title, content = note.content, isLoading = false)
                }
            }
        }
    }

    fun onTitleChange(newTitle: String) {
        _uiState.update { it.copy(title = newTitle, titleError = null) }
    }

    fun onContentChange(newContent: String) {
        _uiState.update { it.copy(content = newContent) }
    }

    fun onSaveClick() {
        val current = _uiState.value

        if (current.title.isBlank()) {
            _uiState.update { it.copy(titleError = "Title cannot be empty") }
            return
        }

        viewModelScope.launch {
            if (noteId != null) {
                repository.updateNote(
                    NoteEntity(id = noteId, title = current.title, content = current.content)
                )
            } else {
                repository.insertNote(
                    NoteEntity(title = current.title, content = current.content)
                )
            }
            _navigateBack.send(Unit)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as NotesApplication
                val savedStateHandle = createSavedStateHandle()
                AddEditNoteViewModel(savedStateHandle, app.repository)
            }
        }
    }
}
