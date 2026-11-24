package com.example.todolist.data.repository

import com.example.todolist.data.local.TodoJsonDataSource
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository

class TodoRepositoryImpl(
    private val jsonDataSource: TodoJsonDataSource
) : TodoRepository {

    private val cachedTodos = mutableListOf<TodoItem>()

    override suspend fun getTodos(): List<TodoItem> {
        if (cachedTodos.isEmpty()) {
            val dtoList = jsonDataSource.getTodos()
            cachedTodos.addAll(dtoList.map { it.toDomain() })
        }
        return cachedTodos.toList()
    }

    override suspend fun toggleTodo(id: Int) {
        val index = cachedTodos.indexOfFirst { it.id == id }
        if (index != -1) {
            val current = cachedTodos[index]
            cachedTodos[index] = current.copy(isCompleted = !current.isCompleted)
        }
    }
}
