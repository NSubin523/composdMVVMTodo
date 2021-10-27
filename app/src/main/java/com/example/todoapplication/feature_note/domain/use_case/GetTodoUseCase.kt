package com.example.todoapplication.feature_note.domain.use_case

import com.example.todoapplication.feature_note.domain.model.TodoNote
import com.example.todoapplication.feature_note.domain.repository.TodoNoteRepository
import com.example.todoapplication.feature_note.domain.util.OrderType
import com.example.todoapplication.feature_note.domain.util.TodoNoteOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTodoUseCase (
    private val repository: TodoNoteRepository ) {

    operator fun invoke(
        noteOrder: TodoNoteOrder
        = TodoNoteOrder.Date(orderType = OrderType.Descending)
    )
            : Flow<List<TodoNote>> {
        return repository.getTodoNotes().map { todoNotes ->
            when (noteOrder.orderType) {
                is OrderType.Ascending -> {
                    when (noteOrder) {
                        is TodoNoteOrder.Title -> todoNotes.sortedBy { it.noteTitle.lowercase() }
                        is TodoNoteOrder.Date -> todoNotes.sortedBy { it.timeStamp }
                        is TodoNoteOrder.Color -> todoNotes.sortedBy { it.color }
                    }
                }
                is OrderType.Descending -> {
                    when (noteOrder) {
                        is TodoNoteOrder.Title -> todoNotes.sortedByDescending { it.noteTitle.lowercase() }
                        is TodoNoteOrder.Date -> todoNotes.sortedByDescending { it.timeStamp }
                        is TodoNoteOrder.Color -> todoNotes.sortedByDescending { it.color }
                    }
                }
            }
        }
    }
}