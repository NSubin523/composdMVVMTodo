package com.example.todoapplication.feature_note.domain.model

import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.todoapplication.ui.theme.BabyBlue
import com.example.todoapplication.ui.theme.RedOrange
import com.example.todoapplication.ui.theme.RedPink
import com.example.todoapplication.ui.theme.Violet
import java.lang.Exception

@Entity
data class TodoNote(
    val noteTitle: String,
    val content: String,
    val timeStamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
)
{
    companion object{
        val todoNoteColors = listOf(RedOrange,LightGray, Violet, BabyBlue, RedPink)
    }
}

class InvalidTodoObjectException(message: String): Exception(message)
