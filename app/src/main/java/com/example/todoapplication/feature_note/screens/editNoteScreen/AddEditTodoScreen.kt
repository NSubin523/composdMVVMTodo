package com.example.todoapplication.feature_note.screens.editNoteScreen

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapplication.feature_note.domain.model.TodoNote
import com.example.todoapplication.feature_note.screens.editNoteScreen.composables.TransparentHintTextField
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditTodoScreen(
    navController: NavController,
    todoColor: Int,
    viewModel: AddEditTodoVM = hiltViewModel()
){
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value
    val scaffoldState = rememberScaffoldState()
    val noteBackgroundChange = remember{
        Animatable(
            Color(if(todoColor!=-1) todoColor else viewModel.noteColor.value)
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event->
            when(event){
                is AddEditTodoVM.UiEvent.ShowSnackBar ->{
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is AddEditTodoVM.UiEvent.SaveTodoNote ->{
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {viewModel.onEvent(AddEditTodoEvent.SaveTodo)},
            backgroundColor = MaterialTheme.colors.primary
        ) {
            Icon(imageVector = Icons.Default.Save, contentDescription = "Save Item")
        }
    }, scaffoldState = scaffoldState) {
        Column( modifier = Modifier
            .fillMaxSize()
            .background(noteBackgroundChange.value)
            .padding(16.dp)) {
            Row( modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), horizontalArrangement = Arrangement.SpaceBetween){
                TodoNote.todoNoteColors.forEach{ color ->
                    val colorNum = color.toArgb()
                    Box( modifier = Modifier
                        .size(50.dp)
                        .shadow(15.dp, CircleShape)
                        .clip(CircleShape)
                        .background(color)
                        .border(
                            width = 3.dp,
                            color =
                            if (viewModel.noteColor.value == colorNum) {
                                Color.Black
                            } else {
                                Color.Transparent
                            },
                            shape = CircleShape
                        )
                        .clickable {
                            scope.launch {
                                noteBackgroundChange.animateTo(
                                    targetValue = Color(colorNum),
                                    animationSpec = tween(
                                        durationMillis = 500
                                    )
                                )
                            }
                            viewModel.onEvent(AddEditTodoEvent.ChangeColor(colorNum))
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = titleState.text,
                hint = titleState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.EnteredTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditTodoEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(16.dp))
            TransparentHintTextField(
                text = contentState.text,
                hint = contentState.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditTodoEvent.EnteredContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditTodoEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}