package com.example.notesapp.data.repository

import com.example.notesapp.data.local.NoteDao
import com.example.notesapp.data.local.NoteEntity
import kotlinx.coroutines.flow.Flow

class NotesRepositoryImpl(
    private val noteDao: NoteDao
) : NotesRepository {

    override fun getAllNotes(): Flow<List<NoteEntity>> {
        return noteDao.getAllNotes()
    }

    override suspend fun getNoteById(noteId: Int): NoteEntity? {
        return noteDao.getNoteById(noteId)
    }

    override suspend fun insertNote(note: NoteEntity) {
        noteDao.insertNote(note)
    }

    override suspend fun updateNote(note: NoteEntity) {
        noteDao.updateNote(note)
    }

    override suspend fun deleteNote(note: NoteEntity) {
        noteDao.deleteNote(note)
    }
}