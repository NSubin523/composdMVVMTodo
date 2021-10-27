package com.example.todoapplication.feature_note.domain.use_case

import com.example.todoapplication.feature_note.domain.model.TodoNote
import com.example.todoapplication.feature_note.domain.repository.TodoNoteRepository

class DeleteTodoUseCase (
    private val repo: TodoNoteRepository
        ){

    suspend operator fun invoke(todoNote: TodoNote){
        repo.deleteNote(todoNote)
    }
}