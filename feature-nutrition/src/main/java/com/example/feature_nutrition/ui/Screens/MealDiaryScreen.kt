package com.example.feature_nutrition.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.feature_nutrition.presentation.NutritionViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import com.example.feature_nutrition.presentation.mvi.NutritionIntent
import com.example.feature_nutrition.presentation.navigation.NutritionRoutes

@Composable
fun MealDiaryScreen(
    navController: NavHostController,
    viewModel: NutritionViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val today = remember { LocalDate.now() }

    // Загружаем записи за сегодня
    LaunchedEffect(today) {
        viewModel.sendIntent(NutritionIntent.LoadEntries(today))
    }

    Column(Modifier.fillMaxSize().padding(16.dp)) {
        // Заголовок с суммой калорий
        val totalCalories = state.entries.sumOf { it.calories }
        Text(
            text = "Дневник за $today — всего калорий: $totalCalories",
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(12.dp))

        // Список записей
        LazyColumn(Modifier.weight(1f)) {
            items(state.entries) { entry ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(Modifier.padding(12.dp)) {
                        Text(
                            text = entry.customName ?: "Блюдо из каталога",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Калории: ${entry.calories}, Б:${entry.protein} Ж:${entry.fat} У:${entry.carbs}",
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = entry.timestamp.toLocalTime().toString(),
                            style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.height(8.dp))
                        TextButton(onClick = {
                            viewModel.sendIntent(NutritionIntent.DeleteEntry(entry.id))
                        }) {
                            Text("Удалить")
                        }
                    }
                }
            }
        }

        // Индикатор загрузки записей
        if (state.isLoadingEntries) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        // Ошибка загрузки записей
        state.entriesError?.let { err ->
            Text(
                text = err,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(8.dp)
            )
        }

        // Кнопка перейти к каталогу рецептов
        Spacer(Modifier.height(12.dp))
        Button(onClick = { navController.navigate(NutritionRoutes.Recipes) }) {
            Text("Добавить приём из каталога")
        }
    }
}