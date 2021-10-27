package com.example.todoapplication.feature_note.screens.noteScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.feature_note.domain.model.TodoNote
import com.example.todoapplication.feature_note.domain.use_case.UseCaseDataClass
import com.example.todoapplication.feature_note.domain.util.OrderType
import com.example.todoapplication.feature_note.domain.util.TodoNoteOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel  @Inject constructor(
    private val todoUseCase: UseCaseDataClass
): ViewModel(){

    private val _state = mutableStateOf(TodoState())
    val state : State<TodoState> = _state
    private var recentlyDeletedNote : TodoNote? = null
    private var getNotesJob: Job? = null

    init {
        getNotes(TodoNoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: TodoNotesEvent){
        when(event){
            is TodoNotesEvent.OrderEvent->{
                if(state.value.noteOrder::class == event.noteOrder::class
                    && state.value.noteOrder.orderType == event.noteOrder.orderType)
                        return
                getNotes(event.noteOrder)
            }
            is TodoNotesEvent.DeleteNoteEvent->{
                viewModelScope.launch {
                    todoUseCase.deleteTodoNotes(event.todoNote)
                    recentlyDeletedNote = event.todoNote
                }
            }
            is TodoNotesEvent.RestoreNote->{
                viewModelScope.launch {
                    todoUseCase.addTodoNote(recentlyDeletedNote?: return@launch)
                    recentlyDeletedNote = null
                }
            }
            is TodoNotesEvent.ToggleNotes->{
                _state.value = state.value.copy(
                    isOrdered = !state.value.isOrdered
                )
            }
        }
    }

    private fun getNotes(noteOrder: TodoNoteOrder){
        getNotesJob?.cancel()
        todoUseCase.getTodoNotes(noteOrder)
            .onEach { todoNotes ->
                _state.value = state.value.copy(
                    todoNotes = todoNotes,
                    noteOrder = noteOrder
                )
            }.launchIn(viewModelScope)
    }

}