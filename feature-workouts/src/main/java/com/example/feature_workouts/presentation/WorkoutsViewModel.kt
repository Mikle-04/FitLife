package com.example.feature_workouts.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.presentatin.BaseMviViewModel
import com.example.feature_workouts.domain.repository.WorkoutsRepository
import com.example.feature_workouts.presentation.mvi.WorkoutsAction
import com.example.feature_workouts.presentation.mvi.WorkoutsIntent
import com.example.feature_workouts.presentation.mvi.WorkoutsReducer
import com.example.feature_workouts.presentation.mvi.WorkoutsState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WorkoutsViewModel(
    private val repo: WorkoutsRepository,
    private val ioDispatcher: CoroutineDispatcher
) : BaseMviViewModel<WorkoutsIntent, WorkoutsState, WorkoutsAction>(
    initialState = WorkoutsState(),
    reducer = WorkoutsReducer
) {
    override fun handleIntent(intent: WorkoutsIntent) {
        when (intent) {
            WorkoutsIntent.LoadList -> loadList()
            WorkoutsIntent.Refresh  -> loadList(force = true)
            is WorkoutsIntent.Select -> select(intent.id)
        }
    }

    private fun loadList(force: Boolean = false) {
        // если pull-to-refresh, можно очистить локальный кэш перед fetch
        dispatch(WorkoutsAction.ListLoading)
        // Берём поток из репозитория, который сам синхронизирует сеть и базу
        repo.getWorkouts()
            .onEach { list ->
                dispatch(WorkoutsAction.ListLoaded(list))
            }
            .launchIn(viewModelScope)
    }

    private fun select(id: String) {
        // Подписка на один элемент, чтобы передать его в стейт
        repo.getWorkoutById(id)
            .onEach { workout ->
                workout?.let { dispatch(WorkoutsAction.Selected(it)) }
            }
            .launchIn(viewModelScope)
    }
}