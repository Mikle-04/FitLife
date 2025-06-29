package com.example.feature_workouts.presentation.mvi

import com.example.core.presentatin.mvi.MviIntent

sealed interface WorkoutsIntent : MviIntent {
    object LoadList            : WorkoutsIntent   // при старте экрана загрузить список
    object Refresh              : WorkoutsIntent   // pull-to-refresh
    data class Select(val id: String) : WorkoutsIntent // юзер выбрал тренировку
}