package com.example.todoapplication.feature_note.domain.repository

import com.example.todoapplication.feature_note.domain.model.TodoNote
import kotlinx.coroutines.flow.Flow

interface TodoNoteRepository {
    fun getTodoNotes() : Flow<List<TodoNote>>

    suspend fun getNoteById(id: Int): TodoNote?

    suspend fun insertNote(note: TodoNote)

    suspend fun deleteNote(note: TodoNote)
}