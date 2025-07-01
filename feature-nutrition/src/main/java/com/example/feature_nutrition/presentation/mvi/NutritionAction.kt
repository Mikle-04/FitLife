package com.example.feature_nutrition.presentation.mvi

import com.example.core.presentatin.mvi.MviAction
import com.example.feature_nutrition.domain.models.MealEntry
import com.example.feature_nutrition.domain.models.Recipe

sealed interface NutritionAction : MviAction {
    // Recipes
    object RecipesLoading             : NutritionAction
    data class RecipesLoaded(val list: List<Recipe>) : NutritionAction
    data class RecipesError(val message: String) : NutritionAction

    // Meal entries
    object EntriesLoading             : NutritionAction
    data class EntriesLoaded(val list: List<MealEntry>) : NutritionAction
    data class EntriesError(val message: String) : NutritionAction

    // Add/Delete
    data class EntryAdded(val entry: MealEntry) : NutritionAction
    data class EntryDeleted(val id: String) : NutritionAction
    data class EntryError(val message: String) : NutritionAction
}