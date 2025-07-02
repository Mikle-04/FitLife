package com.example.feature_nutrition.presentation.mvi

import com.example.core.presentatin.mvi.MviState
import com.example.feature_nutrition.domain.models.MealEntry
import com.example.feature_nutrition.domain.models.Recipe

data class NutritionState(
    val isLoadingRecipes: Boolean = false,
    val recipes: List<Recipe> = emptyList(),
    val recipesError: String? = null,

    val isLoadingEntries: Boolean = false,
    val entries: List<MealEntry> = emptyList(),
    val entriesError: String? = null,

    val lastActionError: String? = null  // для ошибок добавления/удаления
) : MviState