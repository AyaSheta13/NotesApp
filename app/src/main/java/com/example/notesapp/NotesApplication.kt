package com.example.notesapp

import android.app.Application
import androidx.room.Room
import com.example.notesapp.data.local.NotesDatabase
import com.example.notesapp.data.repository.NotesRepository
import com.example.notesapp.data.repository.NotesRepositoryImpl

class NotesApplication : Application() {
    val database: NotesDatabase by lazy {
        Room.databaseBuilder(this, NotesDatabase::class.java, "notes.db").build()
    }
    val repository: NotesRepository by lazy {
        NotesRepositoryImpl(database.noteDao())
    }
}