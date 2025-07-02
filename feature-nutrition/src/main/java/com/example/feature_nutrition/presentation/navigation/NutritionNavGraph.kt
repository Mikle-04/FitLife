package com.example.feature_nutrition.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.feature_nutrition.presentation.NutritionViewModel
import com.example.feature_nutrition.ui.Screens.MealDiaryScreen
import com.example.feature_nutrition.ui.Screens.RecipesListScreen
import org.koin.androidx.compose.koinViewModel

/**
 * Расширение для подключения навигации по модулю питания
 */
fun NavGraphBuilder.nutritionGraph(
    navController: NavHostController
) {
    // 1. Экран каталога рецептов
    composable(NutritionRoutes.Recipes) {
        val vm: NutritionViewModel = koinViewModel()
        RecipesListScreen(
            navController = navController,
            viewModel     = vm
        )
    }

    // 2. Экран дневника питания за текущую дату
    composable(NutritionRoutes.Diary) {
        val vm: NutritionViewModel = koinViewModel()
        MealDiaryScreen(
            navController = navController,
            viewModel     = vm
        )
    }
}