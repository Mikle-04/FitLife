package com.example.feature_nutrition.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.presentatin.BaseMviViewModel
import com.example.feature_nutrition.domain.models.MealEntry
import com.example.feature_nutrition.domain.models.repository.NutritionRepository
import com.example.feature_nutrition.presentation.mvi.NutritionAction
import com.example.feature_nutrition.presentation.mvi.NutritionIntent
import com.example.feature_nutrition.presentation.mvi.NutritionReducer
import com.example.feature_nutrition.presentation.mvi.NutritionState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.time.LocalDate
import kotlinx.coroutines.flow.single

class NutritionViewModel(
    private val repo: NutritionRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BaseMviViewModel<NutritionIntent, NutritionState, NutritionAction>(
    initialState = NutritionState(),
    reducer = NutritionReducer
) {
    override fun handleIntent(intent: NutritionIntent) {
        when (intent) {
            NutritionIntent.LoadRecipes -> loadRecipes()
            is NutritionIntent.LoadEntries -> loadEntries(intent.date)
            is NutritionIntent.AddEntry -> addEntry(intent)
            is NutritionIntent.DeleteEntry -> deleteEntry(intent.id)
        }
    }

    private fun loadRecipes() {
        dispatch(NutritionAction.RecipesLoading)
        // оборачиваем suspend- запрос в flow
        flow {
            val list = repo.getRecipes().single()
            emit(list)
        }
            .onEach { dispatch(NutritionAction.RecipesLoaded(it)) }
            .launchIn(viewModelScope)
    }

    private fun loadEntries(date: LocalDate) {
        dispatch(NutritionAction.EntriesLoading)
        repo.getMealEntriesForDate(date)
            .onEach { dispatch(NutritionAction.EntriesLoaded(it)) }
            .launchIn(viewModelScope)
    }

    private fun addEntry(intent: NutritionIntent.AddEntry) {
        viewModelScope.launch(ioDispatcher) {
            try {
                val entry = MealEntry(
                    id = java.util.UUID.randomUUID().toString(),
                    recipeId = intent.recipeId,
                    customName = intent.customName,
                    calories = intent.calories,
                    protein = intent.protein,
                    fat = intent.fat,
                    carbs = intent.carbs,
                    timestamp = intent.date.atStartOfDay()
                )
                repo.addMealEntry(entry)
                dispatch(NutritionAction.EntryAdded(entry))
            } catch (e: Exception) {
                dispatch(NutritionAction.EntryError(e.localizedMessage ?: "Error"))
            }
        }
    }

    private fun deleteEntry(id: String) {
        viewModelScope.launch(ioDispatcher) {
            try {
                repo.deleteMealEntry(id)
                dispatch(NutritionAction.EntryDeleted(id))
            } catch (e: Exception) {
                dispatch(NutritionAction.EntryError(e.localizedMessage ?: "Error"))
            }
        }
    }
}