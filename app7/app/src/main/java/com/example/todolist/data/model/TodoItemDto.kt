package com.example.todolist.data.model

import com.example.todolist.domain.model.TodoItem

data class TodoItemDto(
    val id: Int,
    val title: String,
    val description: String,
    val isCompleted: Boolean
) {
    fun toDomain(): TodoItem = TodoItem(
        id = id,
        title = title,
        description = description,
        isCompleted = isCompleted
    )
}
