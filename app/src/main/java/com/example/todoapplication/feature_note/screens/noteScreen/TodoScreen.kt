package com.example.todoapplication.feature_note.screens.noteScreen

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapplication.feature_note.screens.noteScreen.composables.OrderSection
import com.example.todoapplication.feature_note.screens.noteScreen.composables.TodoItem
import com.example.todoapplication.feature_note.screens.util.ScreenNavs
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun TodoScreen(
    navController: NavController,
    viewModel: TodoViewModel = hiltViewModel()
){
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold (floatingActionButton = {
        FloatingActionButton(onClick = {
           navController.navigate(ScreenNavs.AddEditNoteScreen.route)
        }, backgroundColor = MaterialTheme.colors.primary){
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
        }
    },
        scaffoldState = scaffoldState
    )
    {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Your Todo Item",
                    style = MaterialTheme.typography.h4
                )
                IconButton(onClick = {
                    viewModel.onEvent(TodoNotesEvent.ToggleNotes)
                }) {
                    Icon(imageVector = Icons.Default.Sort, contentDescription = "Sort" )
                }
            }
            AnimatedVisibility(visible = state.isOrdered, enter = fadeIn() + slideInVertically(), exit = fadeOut() + slideOutVertically()) {
                OrderSection(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp), noteOrder = state.noteOrder, onOrderChange = {
                    viewModel.onEvent(TodoNotesEvent.OrderEvent(it))
                } )
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize()){
                items(state.todoNotes){ todo->
                    TodoItem(
                        todo = todo,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                navController.navigate(
                                    ScreenNavs.AddEditNoteScreen.route + "?id=${todo.id}&color=${todo.color}"
                                )
                            },
                    onDeleteClick = {
                        viewModel.onEvent(TodoNotesEvent.DeleteNoteEvent(todo))
                        scope.launch {
                            val res = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Note deleted!!",
                                actionLabel = "Undo"
                            )
                            if (res == SnackbarResult.ActionPerformed){
                                viewModel.onEvent(TodoNotesEvent.RestoreNote)
                            }
                        }
                    })
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}