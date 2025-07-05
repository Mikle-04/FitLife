package com.example.feature_nutrition.data.repository

import com.example.feature_nutrition.domain.models.MealEntry
import com.example.feature_nutrition.domain.models.Recipe
import com.example.feature_nutrition.domain.models.repository.NutritionRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.time.LocalDateTime

class FakeNutritionRepository : NutritionRepository {
    private val sampleRecipes = listOf(
        Recipe("r1", "Овсянка", 150, 5, 3, 27),
        Recipe("r2", "Яйцо вкрутую", 70, 6, 5, 0),
        Recipe("r3", "Смузи", 200, 2, 1, 48)
    )

    override fun getRecipes(): Flow<List<Recipe>> = flow {
        emit(emptyList())
        delay(400)
        emit(sampleRecipes)
    }

    override fun getMealEntriesForDate(date: LocalDate): Flow<List<MealEntry>> = flow {
        // сначала пусто, потом эмулируем одну запись
        emit(emptyList())
        delay(300)
        emit(listOf(
            MealEntry(
                id = "e1",
                recipeId = "r1",
                customName = null,
                calories = 150,
                protein = 5,
                fat = 3,
                carbs = 27,
                timestamp = LocalDateTime.now()
            )
        ))
    }


    override suspend fun addMealEntry(entry: MealEntry) {
        // ничего не сохраняем — заглушка
    }

    override suspend fun deleteMealEntry(id: String) {
        // ничего не удаляем
    }
}