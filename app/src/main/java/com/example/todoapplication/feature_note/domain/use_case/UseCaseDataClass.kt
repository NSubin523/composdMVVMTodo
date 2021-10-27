package com.example.todoapplication.feature_note.domain.use_case

data class UseCaseDataClass(
    val getTodoNotes: GetTodoUseCase,
    val deleteTodoNotes: DeleteTodoUseCase,
    val addTodoNote: AddTodoNote,
    val getSingleTodoNote: GetTodo
)
