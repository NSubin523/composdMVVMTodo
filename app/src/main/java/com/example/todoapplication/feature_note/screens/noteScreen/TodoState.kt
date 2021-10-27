package com.example.todoapplication.feature_note.screens.noteScreen

import com.example.todoapplication.feature_note.domain.model.TodoNote
import com.example.todoapplication.feature_note.domain.util.OrderType
import com.example.todoapplication.feature_note.domain.util.TodoNoteOrder

data class TodoState(
    val todoNotes: List<TodoNote> = emptyList(),
    val noteOrder: TodoNoteOrder = TodoNoteOrder.Date(OrderType.Descending),
    val isOrdered: Boolean = false
)
