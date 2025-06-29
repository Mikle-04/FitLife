package com.example.feature_workouts.presentation.mvi

import com.example.core.presentatin.mvi.MviAction
import com.example.feature_workouts.domain.models.Workout

sealed interface WorkoutsAction : MviAction {
    object ListLoading            : WorkoutsAction                // старт загрузки списка
    data class ListLoaded(val list: List<Workout>) : WorkoutsAction  // получили данные
    data class ListError(val message: String) : WorkoutsAction      // ошибка при загрузке

    data class Selected(val workout: Workout) : WorkoutsAction      // выбрали одну тренировку
}