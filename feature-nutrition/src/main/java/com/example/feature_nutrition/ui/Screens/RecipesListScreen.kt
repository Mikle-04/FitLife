package com.example.feature_nutrition.ui.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.foundation.lazy.items

@Composable
fun RecipesListScreen(
    navController: NavHostController,
    viewModel: NutritionViewModel = koinViewModel()
) {
    // Подписываемся на state
    val state by viewModel.state.collectAsState()

    // Текущая дата для записи приёма
    val today = remember { LocalDate.now() }

    // При первом старте экрана загружаем рецепты
    LaunchedEffect(Unit) {
        viewModel.sendIntent(NutritionIntent.LoadRecipes)
    }

    Box(Modifier.fillMaxSize()) {
        // Список рецептов
        LazyColumn(Modifier.fillMaxSize().padding(16.dp)) {
            items(state.recipes) { recipe ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            // При клике добавляем запись в дневник и уходим в Diary
                            viewModel.sendIntent(
                                NutritionIntent.AddEntry(
                                    date      = today,
                                    calories  = recipe.calories,
                                    protein   = recipe.protein,
                                    fat       = recipe.fat,
                                    carbs     = recipe.carbs,
                                    recipeId  = recipe.id,
                                    customName= null
                                )
                            )
                            navController.navigate(NutritionRoutes.Diary)
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(text = recipe.name, style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = "Калории: ${recipe.calories}, Б:${recipe.protein} Ж:${recipe.fat} У:${recipe.carbs}",
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }

        // Индикатор загрузки
        if (state.isLoadingRecipes) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }

        // Ошибка загрузки
        state.recipesError?.let { err ->
            Text(
                text = err,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.align(Alignment.Center).padding(16.dp)
            )
        }
    }
}