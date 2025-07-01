package com.example.feature_nutrition.data.remote

import com.example.feature_nutrition.domain.models.Recipe
import retrofit2.http.GET

data class RecipeDto(
    val id: String,
    val name: String,
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carbs: Int
)

interface NutritionApi {
    @GET("recipes")
    suspend fun fetchRecipes(): List<RecipeDto>
}

// Маппер
fun RecipeDto.toModel() = Recipe(
    id = id,
    name = name,
    calories = calories,
    protein = protein,
    fat = fat,
    carbs = carbs
)