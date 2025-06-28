package com.example.feature_workouts.domain.repository

import com.example.feature_workouts.domain.models.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutsRepository {
    /** Возвращаем поток списка тренировок из базы (подсинхронизированных с сетью) */
    fun getWorkouts(): Flow<List<Workout>>

    /** Возвращаес поток одной тренировки по id */
    fun getWorkoutById(id: String): Flow<Workout?>
}