package com.example.feature_workouts.presentation.mvi

import com.example.core.presentatin.mvi.MviState
import com.example.feature_workouts.domain.models.Workout

data class WorkoutsState(
    val isLoading: Boolean = false,
    val list: List<Workout> = emptyList(),  // список тренировок для отображения
    val error: String? = null,              // текст ошибки
    val selected: Workout? = null           // выбранная тренировка (для перехода на detail)
) : MviState