package com.example.feature_nutrition.domain.models

data class Recipe(
    val id: String,
    val name: String,
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carbs: Int
)
