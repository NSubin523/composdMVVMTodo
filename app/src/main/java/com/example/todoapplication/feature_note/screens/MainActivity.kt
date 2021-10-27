package com.example.todoapplication.feature_note.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.todoapplication.feature_note.screens.editNoteScreen.AddEditTodoScreen
import com.example.todoapplication.feature_note.screens.noteScreen.TodoScreen
import com.example.todoapplication.feature_note.screens.util.ScreenNavs
import com.example.todoapplication.ui.theme.TodoApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApplicationTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ){
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ScreenNavs.TodoScreen.route
                        ){
                        composable(route=ScreenNavs.TodoScreen.route){
                            TodoScreen(navController = navController)
                        }
                        composable(
                            route=ScreenNavs.AddEditNoteScreen.route + "?id={id}&color={color}",
                            arguments = listOf(
                                navArgument(
                                    name="id"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name="color"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                            ){
                            val color = it.arguments?.getInt("color") ?: -1
                            AddEditTodoScreen(navController = navController , todoColor = color )
                        }
                    }
                }
            }
        }
    }
}
