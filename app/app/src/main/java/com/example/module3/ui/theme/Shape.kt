package com.example.module3.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

// Пользовательские формы для компонентов согласно Заданию 2
val Shapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(
        topStart = 12.dp,
        topEnd = 32.dp,
        bottomStart = 32.dp,
        bottomEnd = 12.dp
    ),
    large = RoundedCornerShape(
        topStart = 24.dp,
        topEnd = 8.dp,
        bottomStart = 24.dp,
        bottomEnd = 8.dp
    )
)
