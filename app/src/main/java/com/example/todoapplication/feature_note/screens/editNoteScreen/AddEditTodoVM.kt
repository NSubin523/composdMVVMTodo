package com.example.todoapplication.feature_note.screens.editNoteScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.feature_note.domain.model.InvalidTodoObjectException
import com.example.todoapplication.feature_note.domain.model.TodoNote
import com.example.todoapplication.feature_note.domain.use_case.UseCaseDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoVM @Inject constructor(
    private val todoUseCase: UseCaseDataClass,
    savedStateHandle: SavedStateHandle
) : ViewModel(){

    private val _noteTile = mutableStateOf(TodoTextFieldState(
        hint = "Enter title....."
    ))
    val noteTitle : State<TodoTextFieldState> = _noteTile

    private val _noteContent = mutableStateOf(TodoTextFieldState(
        hint = "Enter some content...."
    ))
    val noteContent : State<TodoTextFieldState> = _noteContent

    private val _noteColor = mutableStateOf<Int>(TodoNote.todoNoteColors.random().toArgb())
    val noteColor : State<Int> = _noteColor

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentTodoId: Int? = null

    init{
        savedStateHandle.get<Int>("id")?.let { id ->
            if(id != -1){
                viewModelScope.launch {
                    todoUseCase.getSingleTodoNote(id)?.also { todo->
                        currentTodoId = todo.id
                        _noteTile.value = noteTitle.value.copy(
                            text = todo.noteTitle,
                            isHintVisible = false
                        )
                        _noteContent.value = noteContent.value.copy(
                            text = todo.content,
                            isHintVisible = false
                        )
                        _noteColor.value = todo.color
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditTodoEvent){
        when (event){
            is AddEditTodoEvent.EnteredTitle ->{
                _noteTile.value = noteTitle.value.copy(
                    text = event.value
                )
            }
            is AddEditTodoEvent.ChangeTitleFocus ->{
                _noteTile.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }
            is AddEditTodoEvent.EnteredContent ->{
                _noteContent.value = noteContent.value.copy(
                    text = event.value
                )
            }
            is AddEditTodoEvent.ChangeContentFocus ->{
                _noteContent.value = noteContent.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteContent.value.text.isBlank()
                )
            }
            is AddEditTodoEvent.ChangeColor ->{
                _noteColor.value = event.color
            }
            is AddEditTodoEvent.SaveTodo ->{
                viewModelScope.launch {
                    try{
                        todoUseCase.addTodoNote(
                            TodoNote(
                                noteTitle = noteTitle.value.text,
                                content = noteContent.value.text,
                                timeStamp = System.currentTimeMillis(),
                                color = noteColor.value,
                                id = currentTodoId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveTodoNote)
                    }catch (e: InvalidTodoObjectException){
                        _eventFlow.emit(UiEvent.ShowSnackBar(
                            message = e.message?: "Unknown error. Try again later"
                        ))
                    }
                }
            }
        }
    }

    sealed class UiEvent{
        data class ShowSnackBar(val message: String): UiEvent()
        object SaveTodoNote : UiEvent()
    }

}