package com.example.todolist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todolist.presentation.ui.screen.TodoDetailScreen
import com.example.todolist.presentation.ui.screen.TodoListScreen
import com.example.todolist.presentation.viewmodel.TodoViewModel

sealed class Screen(val route: String) {
    data object TodoList : Screen("todo_list")
    data object TodoDetail : Screen("todo_detail/{todoId}") {
        fun createRoute(todoId: Int): String = "todo_detail/$todoId"
    }
}

@Composable
fun TodoNavGraph(
    navController: NavHostController,
    viewModel: TodoViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.TodoList.route,
        modifier = modifier
    ) {
        composable(route = Screen.TodoList.route) {
            TodoListScreen(
                viewModel = viewModel,
                onNavigateToDetail = { id ->
                    navController.navigate(Screen.TodoDetail.createRoute(id))
                }
            )
        }

        composable(
            route = Screen.TodoDetail.route,
            arguments = listOf(
                navArgument("todoId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val todoId = backStackEntry.arguments?.getInt("todoId") ?: -1
            val todos by viewModel.todos.collectAsState()
            val item = todos.find { it.id == todoId }

            TodoDetailScreen(
                todoItem = item,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
