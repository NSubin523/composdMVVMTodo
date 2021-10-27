package com.example.todoapplication.feature_note.data.data_source

import androidx.room.*
import com.example.todoapplication.feature_note.domain.model.TodoNote
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDAO {

    @Query("SELECT * FROM TodoNote")
    fun getTodoNotes(): Flow<List<TodoNote>>

    @Query("SELECT * FROM TodoNote WHERE id=:id")
    suspend fun getNoteById (id: Int): TodoNote?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: TodoNote)

    @Delete
    suspend fun deleteNote(note: TodoNote)
}