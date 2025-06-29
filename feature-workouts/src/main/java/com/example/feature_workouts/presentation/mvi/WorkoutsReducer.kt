package com.example.feature_workouts.presentation.mvi

import com.example.core.presentatin.mvi.MviReducer

object WorkoutsReducer : MviReducer<WorkoutsState, WorkoutsAction> {
    override fun reduce(state: WorkoutsState, action: WorkoutsAction): WorkoutsState =
        when (action) {
            WorkoutsAction.ListLoading ->
                state.copy(isLoading = true, error = null)

            is WorkoutsAction.ListLoaded ->
                state.copy(isLoading = false, list = action.list)

            is WorkoutsAction.ListError ->
                state.copy(isLoading = false, error = action.message)

            is WorkoutsAction.Selected ->
                state.copy(selected = action.workout)

        }
}