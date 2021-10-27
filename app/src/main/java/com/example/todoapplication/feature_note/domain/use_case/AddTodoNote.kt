package com.example.todoapplication.feature_note.domain.use_case

import com.example.todoapplication.feature_note.domain.model.InvalidTodoObjectException
import com.example.todoapplication.feature_note.domain.model.TodoNote
import com.example.todoapplication.feature_note.domain.repository.TodoNoteRepository

class AddTodoNote (
    private val repo: TodoNoteRepository
    ){

    @Throws(InvalidTodoObjectException::class)
    suspend operator fun invoke(todoNote: TodoNote){
        if(todoNote.noteTitle.isBlank()){
            throw InvalidTodoObjectException("Title of Todo can't be empty")
        }
        if(todoNote.content.isBlank()){
            throw InvalidTodoObjectException("Content of Todo can't be empty")
        }
        repo.insertNote(todoNote)
    }

}