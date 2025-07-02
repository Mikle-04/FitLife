package com.example.feature_nutrition.presentation.mvi

import com.example.core.presentatin.mvi.MviReducer

object NutritionReducer : MviReducer<NutritionState, NutritionAction> {
    override fun reduce(state: NutritionState, action: NutritionAction): NutritionState =
        when (action) {
            NutritionAction.RecipesLoading ->
                state.copy(isLoadingRecipes = true, recipesError = null)

            is NutritionAction.RecipesLoaded ->
                state.copy(isLoadingRecipes = false, recipes = action.list)

            is NutritionAction.RecipesError ->
                state.copy(isLoadingRecipes = false, recipesError = action.message)

            NutritionAction.EntriesLoading ->
                state.copy(isLoadingEntries = true, entriesError = null)

            is NutritionAction.EntriesLoaded ->
                state.copy(isLoadingEntries = false, entries = action.list)

            is NutritionAction.EntriesError ->
                state.copy(isLoadingEntries = false, entriesError = action.message)

            is NutritionAction.EntryAdded ->
                // можно опционально обновить entries: state.entries + action.entry
                state

            is NutritionAction.EntryDeleted ->
                // можно опционально фильтровать entries по id
                state

            is NutritionAction.EntryError ->
                state.copy(lastActionError = action.message)
        }
}