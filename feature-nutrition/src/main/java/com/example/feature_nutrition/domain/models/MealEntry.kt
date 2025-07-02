package com.example.feature_nutrition.domain.models

import java.time.LocalDateTime

data class MealEntry(
    val id: String,
    val recipeId: String?,        // если блюдо из каталога
    val customName: String?,      // если пользователь добавил вручную
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carbs: Int,
    val timestamp: LocalDateTime
)
