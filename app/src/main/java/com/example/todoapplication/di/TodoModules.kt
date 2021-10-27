package com.example.todoapplication.di

import android.app.Application
import androidx.room.Room
import com.example.todoapplication.feature_note.data.data_source.TodoDatabase
import com.example.todoapplication.feature_note.data.repository.TodoRepoImpl
import com.example.todoapplication.feature_note.domain.repository.TodoNoteRepository
import com.example.todoapplication.feature_note.domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModules {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase{
        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            TodoDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(database: TodoDatabase): TodoNoteRepository{
        return TodoRepoImpl(database.dao)
    }

    @Provides
    @Singleton
    fun provideTodoUseCases(repo: TodoNoteRepository): UseCaseDataClass{
        return UseCaseDataClass(
            getTodoNotes = GetTodoUseCase(repo),
            deleteTodoNotes = DeleteTodoUseCase(repo),
            addTodoNote = AddTodoNote(repo),
            getSingleTodoNote = GetTodo(repo)
        )
    }
}