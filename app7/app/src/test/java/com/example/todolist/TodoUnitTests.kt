package com.example.todolist

import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository
import com.example.todolist.domain.usecase.GetTodosUseCase
import com.example.todolist.domain.usecase.ToggleTodoUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Фейковый репозиторий для изоляции Unit-тестов без использования Android Context
 */
class FakeTodoRepository : TodoRepository {
    private val todos = mutableListOf(
        TodoItem(id = 1, title = "Купить молоко", description = "2 литра, обезжиренное", isCompleted = false),
        TodoItem(id = 2, title = "Позвонить маме", description = "Спросить про выходные", isCompleted = true),
        TodoItem(id = 3, title = "Сделать ДЗ по Android", description = "Clean Architecture + Compose", isCompleted = false)
    )

    override suspend fun getTodos(): List<TodoItem> {
        return todos.toList()
    }

    override suspend fun toggleTodo(id: Int) {
        val index = todos.indexOfFirst { it.id == id }
        if (index != -1) {
            val item = todos[index]
            todos[index] = item.copy(isCompleted = !item.isCompleted)
        }
    }
}

class TodoUnitTests {

    private lateinit var repository: TodoRepository
    private lateinit var getTodosUseCase: GetTodosUseCase
    private lateinit var toggleTodoUseCase: ToggleTodoUseCase

    @Before
    fun setUp() {
        repository = FakeTodoRepository()
        getTodosUseCase = GetTodosUseCase(repository)
        toggleTodoUseCase = ToggleTodoUseCase(repository)
    }

    /**
     * 1) Unit-тест: GetTodosUseCase возвращает 3 задачи
     */
    @Test
    fun getTodosUseCase_returnsThreeTodos() = runTest {
        val todos = getTodosUseCase()

        assertEquals(3, todos.size)
        assertEquals("Купить молоко", todos[0].title)
        assertEquals("Позвонить маме", todos[1].title)
        assertEquals("Сделать ДЗ по Android", todos[2].title)
    }

    /**
     * 2) Unit-тест: toggleTodo меняет isCompleted
     */
    @Test
    fun toggleTodoUseCase_changesIsCompleted() = runTest {
        // Проверяем начальное состояние задачи 1 (isCompleted == false)
        val initialTodos = getTodosUseCase()
        assertFalse(initialTodos[0].isCompleted)

        // Выполняем toggleTodo для задачи 1
        toggleTodoUseCase(1)

        // Проверяем, что флаг сменился на true
        val updatedTodos = getTodosUseCase()
        assertTrue(updatedTodos[0].isCompleted)

        // Повторный toggle должен вернуть false
        toggleTodoUseCase(1)
        val revertedTodos = getTodosUseCase()
        assertFalse(revertedTodos[0].isCompleted)
    }
}
