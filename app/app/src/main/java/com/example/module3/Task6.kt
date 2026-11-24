package com.example.module3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.module3.ui.theme.MyApplicationTheme

/**
 * Элемент списка кораблей для выбора
 */
@Composable
fun AdaptiveShipItem(
    item: CardItem,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.primaryContainer
            } else {
                MaterialTheme.colorScheme.surfaceVariant
            },
            contentColor = if (isSelected) {
                MaterialTheme.colorScheme.onPrimaryContainer
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isSelected) 6.dp else 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .clickable { onSelect() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2
            )
        }
    }
}

/**
 * Панель детального описания выбранного элемента
 */
@Composable
fun ShipDetailPane(
    item: CardItem?,
    modifier: Modifier = Modifier
) {
    Card(
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (item != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
                )
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Выберите корабль из списка слева для просмотра деталей",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

/**
 * Адаптивный экранчик тут у нас крутейший:
 * - Compact: одноколоночный макет (список)
 * - Medium / Expanded: двухколоночный макет (список слева + детали справа)
 */
@Composable
fun Task6AdaptiveScreen(
    windowWidthSizeClass: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    var selectedItem by remember { mutableStateOf<CardItem?>(shipList.firstOrNull()) }

    when (windowWidthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            // Одноколоночный макет для телефонов в портретной ориентации
            Column(modifier = modifier.fillMaxSize()) {
                Text(
                    text = "Режим: Compact (одна колонка)",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(shipList) { ship ->
                        AdaptiveShipItem(
                            item = ship,
                            isSelected = ship == selectedItem,
                            onSelect = { selectedItem = ship }
                        )
                    }
                }
            }
        }
        WindowWidthSizeClass.Medium, WindowWidthSizeClass.Expanded -> {
            // Двухколоночный макет для планшетов и альбомной ориентации (List - Detail)
            Column(modifier = modifier.fillMaxSize()) {
                Text(
                    text = "Режим: ${if (windowWidthSizeClass == WindowWidthSizeClass.Medium) "Medium" else "Expanded"} (две колонки)",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    // Колонка 1: список
                    LazyColumn(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(shipList) { ship ->
                            AdaptiveShipItem(
                                item = ship,
                                selectedItem == ship,
                                onSelect = { selectedItem = ship }
                            )
                        }
                    }

                    // Колонка 2: детальная информация
                    ShipDetailPane(
                        item = selectedItem,
                        modifier = Modifier
                            .weight(1.2f)
                            .fillMaxHeight()
                    )
                }
            }
        }
        else -> {
            // Fallback для нестандартных классов
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(shipList) { ship ->
                    AdaptiveShipItem(
                        item = ship,
                        isSelected = false,
                        onSelect = {}
                    )
                }
            }
        }
    }
}

/**
 * Preview: Compact
 */
@Preview(name = "Task 6 - Compact (Phone Portrait)", device = "spec:parent=pixel_5,orientation=portrait", showBackground = true, showSystemUi = true)
@Composable
fun Task6CompactPreview() {
    MyApplicationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Task6AdaptiveScreen(
                windowWidthSizeClass = WindowWidthSizeClass.Compact,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

/**
 * Preview: Medium
 */
@Preview(name = "Task 6 - Medium / Expanded (Two Columns)", device = "spec:parent=pixel_5,orientation=landscape", showBackground = true, showSystemUi = true)
@Composable
fun Task6ExpandedPreview() {
    MyApplicationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Task6AdaptiveScreen(
                windowWidthSizeClass = WindowWidthSizeClass.Expanded,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
