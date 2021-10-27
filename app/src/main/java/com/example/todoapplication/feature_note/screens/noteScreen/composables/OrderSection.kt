package com.example.todoapplication.feature_note.screens.noteScreen.composables

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todoapplication.feature_note.domain.util.OrderType
import com.example.todoapplication.feature_note.domain.util.TodoNoteOrder

@Composable
fun OrderSection(modifier: Modifier,
                 noteOrder: TodoNoteOrder = TodoNoteOrder.Date(OrderType.Descending),
                 onOrderChange: (TodoNoteOrder) -> Unit
){

        Column (modifier = modifier){
            Row(modifier = Modifier.fillMaxWidth()) {
                DefaultRadioButton(text = "Title",
                                   selected = noteOrder is TodoNoteOrder.Title,
                                   onSelect = {onOrderChange(TodoNoteOrder.Title(noteOrder.orderType))}
                                  )
                Spacer(modifier = Modifier.width(8.dp))
                DefaultRadioButton(text = "Date",
                    selected = noteOrder is TodoNoteOrder.Date,
                    onSelect = {onOrderChange(TodoNoteOrder.Date(noteOrder.orderType))}
                )
                Spacer(modifier = Modifier.width(8.dp))
                DefaultRadioButton(text = "Color",
                    selected = noteOrder is TodoNoteOrder.Color,
                    onSelect = {onOrderChange(TodoNoteOrder.Color(noteOrder.orderType))}
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                DefaultRadioButton(text = "Ascending",
                    selected = noteOrder.orderType is OrderType.Ascending,
                    onSelect = {onOrderChange(noteOrder.copy(OrderType.Ascending))}
                )
                Spacer(modifier = Modifier.width(8.dp))
                DefaultRadioButton(text = "Descending",
                    selected = noteOrder.orderType is OrderType.Descending,
                    onSelect = {onOrderChange(noteOrder.copy(OrderType.Descending))}
                )
            }
        }
}