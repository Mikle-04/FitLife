package com.example.feature_nutrition.domain.models.repository

import com.example.feature_nutrition.domain.models.MealEntry
import com.example.feature_nutrition.domain.models.Recipe
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface NutritionRepository {
    /** Список рецептов из внешнего API */
    fun getRecipes(): Flow<List<Recipe>>

    /** Дневник: все приёмы пищи за дату */
    fun getMealEntriesForDate(date: LocalDate): Flow<List<MealEntry>>

    /** Добавить/обновить приём пищи */
    suspend fun addMealEntry(entry: MealEntry)

    /** Удалить приём пищи */
    suspend fun deleteMealEntry(id: String)
}