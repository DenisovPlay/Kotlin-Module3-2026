package com.example.todolist

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class TodoUiTests {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    /**
     * 3) UI-тест: отображаются все 3 задачи из JSON
     */
    @Test
    fun todoList_displaysAllThreeTasksFromJson() {
        composeTestRule.onNodeWithText("Купить молоко").assertIsDisplayed()
        composeTestRule.onNodeWithText("Позвонить маме").assertIsDisplayed()
        composeTestRule.onNodeWithText("Сделать ДЗ по Android").assertIsDisplayed()
    }

    /**
     * 4) UI-тест: чекбокс переключает статус
     */
    @Test
    fun todoList_checkboxTogglesStatus() {
        // У задачи 1 начальное состояние - false (unchecked)
        val checkboxNode = composeTestRule.onNodeWithContentDescription("Checkbox_1")
        checkboxNode.assertIsOff()

        // Кликаем по чекбоксу
        checkboxNode.performClick()

        // Проверяем, что теперь статус включен (checked)
        checkboxNode.assertIsOn()

        // Кликаем ещё раз - возвращается в unchecked
        checkboxNode.performClick()
        checkboxNode.assertIsOff()
    }

    /**
     * 5) UI-тест: навигация List -> Detail -> List
     */
    @Test
    fun todoList_navigationFlowListToDetailAndBack() {
        // Проверяем, что мы на экране списка
        composeTestRule.onNodeWithText("Список задач").assertIsDisplayed()

        // Кликаем на задачу 1
        composeTestRule.onNodeWithText("Купить молоко").performClick()

        // Проверяем, что отобразился экран деталей
        composeTestRule.onNodeWithText("Детали задачи").assertIsDisplayed()
        composeTestRule.onNodeWithText("2 литра, обезжиренное").assertIsDisplayed()

        // Нажимаем на кнопку "Назад" в тулбаре
        composeTestRule.onNodeWithContentDescription("BackButton").performClick()

        // Проверяем, что вернулись обратно на экран списка задач
        composeTestRule.onNodeWithText("Список задач").assertIsDisplayed()
        composeTestRule.onNodeWithText("Купить молоко").assertIsDisplayed()
    }
}
