package com.example.feature_workouts.presentation.navigation

/** Пути для графа тренировок */
object WorkoutsRoutes {
    const val List   = "workouts/list"
    const val Detail = "workouts/detail/{workoutId}"

    /** метод для навигации на Detail */
    fun detailRoute(id: String) = "workouts/detail/$id"
}