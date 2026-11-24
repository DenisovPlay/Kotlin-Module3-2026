package com.example.module3

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.module3.ui.theme.MyApplicationTheme

/**
 * Data class
 */
data class CardItem(
    val title: String,
    val description: String,
    @DrawableRes val imageResource: Int
)

/**
 * Коллекция карточек
 */
val shipList = listOf(
    CardItem(
        title = "Mercury Star Runner",
        description = "Скоростной курьерский корабль с выделенными серверными стойками для хранения данных и защищёнными трюмами.",
        imageResource = R.drawable.ic_ship_mercury
    ),
    CardItem(
        title = "C2 Hercules Starlifter",
        description = "Тяжёлый военно-транспортный корабль для безопасной доставки крупногабаритной техники и массивных грузов.",
        imageResource = R.drawable.ic_ship_hercules
    ),
    CardItem(
        title = "Ares Star Fighter",
        description = "Ударный перехватчик с мощной баллистической пушкой седьмого калибра, предназначенный для штурма флагманов.",
        imageResource = R.drawable.ic_ship_ares
    ),
    CardItem(
        title = "RSI Mantis",
        description = "Высокотехнологичный перехватчик, способный генерировать квантовое поле для прерывания прыжков вражеских судов.",
        imageResource = R.drawable.ic_ship_mantis
    ),
    CardItem(
        title = "RSI Aurora MR",
        description = "Универсальный стартовый корабль для базовых грузоперевозок, ближней разведки и патрульных миссий.",
        imageResource = R.drawable.ic_ship_aurora
    ),
    CardItem(
        title = "Drake Cutlass Black",
        description = "Надёжный многоцелевой корабль с внушительным арсеналом вооружения и просторным десантным отсеком.",
        imageResource = R.drawable.ic_ship_cutlass
    )
)

/**
 * Компонент карточки
 */
@Composable
fun ListItemCard(item: CardItem, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.imageResource),
                contentDescription = item.title,
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Экран LazyColumn
 */
@Composable
fun Task1Screen(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 12.dp)
    ) {
        items(items = shipList) { item ->
            ListItemCard(item = item)
        }
    }
}

/**
 * Preview
 */
@Preview(name = "Task 1 - Scrollable List", showBackground = true, showSystemUi = true)
@Composable
fun Task1Preview() {
    MyApplicationTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Task1Screen(modifier = Modifier.padding(innerPadding))
        }
    }
}
