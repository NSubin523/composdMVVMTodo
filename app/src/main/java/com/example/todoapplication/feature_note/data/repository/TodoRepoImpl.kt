package com.example.todoapplication.feature_note.data.repository

import com.example.todoapplication.feature_note.data.data_source.TodoDAO
import com.example.todoapplication.feature_note.domain.model.TodoNote
import com.example.todoapplication.feature_note.domain.repository.TodoNoteRepository
import kotlinx.coroutines.flow.Flow

class TodoRepoImpl(
    private val dao: TodoDAO
): TodoNoteRepository {
    override fun getTodoNotes(): Flow<List<TodoNote>> {
        return dao.getTodoNotes()
    }

    override suspend fun getNoteById(id: Int): TodoNote? {
        return dao.getNoteById(id)
    }

    override suspend fun insertNote(note: TodoNote) {
        return dao.insertNote(note)
    }

    override suspend fun deleteNote(note: TodoNote) {
        return dao.deleteNote(note)
    }
}