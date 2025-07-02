package com.example.feature_nutrition.data.repository

import com.example.feature_nutrition.data.db.MealEntryDao
import com.example.feature_nutrition.data.db.toEntity
import com.example.feature_nutrition.data.db.toModel
import com.example.feature_nutrition.data.remote.NutritionApi
import com.example.feature_nutrition.data.remote.toModel
import com.example.feature_nutrition.domain.models.MealEntry
import com.example.feature_nutrition.domain.models.Recipe
import com.example.feature_nutrition.domain.models.repository.NutritionRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.LocalDate

class NutritionRepositoryImpl(
    private val api: NutritionApi,
    private val dao: MealEntryDao,
    private val ioDispatcher: CoroutineDispatcher
) : NutritionRepository {

    override fun getRecipes(): Flow<List<Recipe>> =
        // оборачиваем fetchRecipes в Flow, кэш здесь не делаем
        kotlinx.coroutines.flow.flow {
            val list = withContext(ioDispatcher) { api.fetchRecipes() }
            emit(list.map { it.toModel() })
        }

    override fun getMealEntriesForDate(date: LocalDate): Flow<List<MealEntry>> =
        dao.getByDate(date)
            .map { list -> list.map { it.toModel() } }

    override suspend fun addMealEntry(entry: MealEntry) =
        withContext(ioDispatcher) {
            dao.upsert(entry.toEntity())
        }

    override suspend fun deleteMealEntry(id: String) =
        withContext(ioDispatcher) {
            dao.deleteById(id)
        }
}