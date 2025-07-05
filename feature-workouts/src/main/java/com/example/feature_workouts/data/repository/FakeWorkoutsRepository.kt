package com.example.feature_workouts.data.repository

import com.example.feature_workouts.domain.models.Workout
import com.example.feature_workouts.domain.repository.WorkoutsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeWorkoutsRepository : WorkoutsRepository {
    override fun getWorkouts(): Flow<List<Workout>> = flow {
        emit(emptyList())
        delay(500)
        emit(listOf(
            Workout("1", "Приседания", "20 повторов", ""),
            Workout("2", "Отжимания",   "15 повторов", ""),
            Workout("3", "Планка",      "60 секунд",  "")
        ))
    }

    override fun getWorkoutById(id: String): Flow<Workout?> = flow {
        delay(300)
        emit(Workout(id, "Тренировка $id", "Заглушка описания", ""))
    }
}