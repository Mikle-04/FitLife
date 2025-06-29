package com.example.feature_workouts.data.repository

import com.example.feature_workouts.data.local.WorkoutDao
import com.example.feature_workouts.data.local.toEntity
import com.example.feature_workouts.data.local.toModel
import com.example.feature_workouts.data.remote.WorkoutApi
import com.example.feature_workouts.domain.models.Workout
import com.example.feature_workouts.domain.repository.WorkoutsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.coroutines.flow.flatMapLatest

class WorkoutsRepositoryImpl(
    private val api: WorkoutApi,
    private val dao: WorkoutDao,
    private val ioDispatcher: CoroutineDispatcher
) : WorkoutsRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getWorkouts(): Flow<List<Workout>> =
        // сначала попытка обновить из сети, затем слушаем локальную базу
        dao.getAll()
            .flatMapLatest { localList ->
                // Если локальный кэш пустой — подгружаем из сети
                if (localList.isEmpty()) {
                    // асинхронно сохранение
                    withContext(ioDispatcher) {
                        val remote = api.fetchAll()
                        dao.insertAll(remote.map { it.toEntity() })
                    }
                }
                dao.getAll().map { list -> list.map { it.toModel() } }
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getWorkoutById(id: String): Flow<Workout?> =
        dao.getById(id)
            .flatMapLatest { entity ->
                if (entity == null) {
                    // если нет в базе — подгружаем из сети
                    withContext(ioDispatcher) {
                        val dto = api.fetchById(id)
                        dao.insertAll(listOf(dto.toEntity()))
                    }
                    dao.getById(id)
                } else
                    dao.getById(id)
            }
            .map { it?.toModel() }
}