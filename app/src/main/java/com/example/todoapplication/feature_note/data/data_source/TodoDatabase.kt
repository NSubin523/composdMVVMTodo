package com.example.todoapplication.feature_note.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapplication.feature_note.domain.model.TodoNote

@Database(
    entities = [TodoNote::class],
    version = 1
)
abstract class TodoDatabase : RoomDatabase() {
    abstract val dao : TodoDAO
    companion object{
        const val DB_NAME = "TodoDB"
    }
}