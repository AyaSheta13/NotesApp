package com.example.notesapp.data.repository

import com.example.notesapp.data.local.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun getAllNotes(): Flow<List<NoteEntity>>
    suspend fun getNoteById(noteId: Int): NoteEntity?
    suspend fun insertNote(note: NoteEntity)
    suspend fun updateNote(note: NoteEntity)
    suspend fun deleteNote(note: NoteEntity)
}