package com.example.todoapplication.feature_note.screens.noteScreen

import com.example.todoapplication.feature_note.domain.model.TodoNote
import com.example.todoapplication.feature_note.domain.util.TodoNoteOrder

sealed class TodoNotesEvent{
    data class OrderEvent(val noteOrder: TodoNoteOrder): TodoNotesEvent()
    data class DeleteNoteEvent(val todoNote: TodoNote): TodoNotesEvent()
    object RestoreNote: TodoNotesEvent()
    object ToggleNotes: TodoNotesEvent()
}
