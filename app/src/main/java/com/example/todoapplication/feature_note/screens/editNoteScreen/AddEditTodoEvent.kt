package com.example.todoapplication.feature_note.screens.editNoteScreen

import androidx.compose.ui.focus.FocusState

sealed class AddEditTodoEvent {
    data class EnteredTitle(val value: String): AddEditTodoEvent()
    data class ChangeTitleFocus(val focusState: FocusState): AddEditTodoEvent()
    data class EnteredContent(val value: String): AddEditTodoEvent()
    data class ChangeContentFocus(val focusState: FocusState): AddEditTodoEvent()
    data class ChangeColor(val color: Int): AddEditTodoEvent()
    object SaveTodo: AddEditTodoEvent()
}
