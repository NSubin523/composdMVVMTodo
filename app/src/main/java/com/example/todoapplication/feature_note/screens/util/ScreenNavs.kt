package com.example.todoapplication.feature_note.screens.util

sealed class ScreenNavs (val route: String){
    object TodoScreen: ScreenNavs("todo_screen")
    object AddEditNoteScreen: ScreenNavs("add_edit_note_screen")
}