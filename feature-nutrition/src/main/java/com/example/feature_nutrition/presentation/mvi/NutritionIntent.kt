package com.example.feature_nutrition.presentation.mvi

import com.example.core.presentatin.mvi.MviIntent
import java.time.LocalDate

sealed interface NutritionIntent : MviIntent {
    object LoadRecipes: NutritionIntent   // загрузить каталог рецептов
    data class LoadEntries(val date: LocalDate) : NutritionIntent // загрузить дневник за дату
    data class AddEntry(val date: LocalDate, val calories: Int, val protein: Int, val fat: Int, val carbs: Int, val recipeId: String? = null, val customName: String? = null) : NutritionIntent
    data class DeleteEntry(val id: String) : NutritionIntent  // удалить приём пищи
}