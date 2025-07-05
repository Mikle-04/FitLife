package com.example.feature_workouts.di

import androidx.room.Room
import com.example.feature_workouts.data.local.WorkoutDao
import com.example.feature_workouts.data.local.WorkoutDatabase
import com.example.feature_workouts.data.remote.WorkoutApi
import com.example.feature_workouts.data.repository.FakeWorkoutsRepository
import com.example.feature_workouts.data.repository.WorkoutsRepositoryImpl
import com.example.feature_workouts.domain.repository.WorkoutsRepository
import com.example.feature_workouts.presentation.WorkoutsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val workoutsModule = module{
    single<WorkoutApi> {
        get<Retrofit>().create(WorkoutApi::class.java)
    }

    single {
        Room.databaseBuilder(
            get(),
            WorkoutDatabase::class.java,
            "workouts_db"
        ).build()
    }

    single<WorkoutDao> {
        get<WorkoutDatabase>().workoutDao()
    }

//    single<WorkoutsRepository> {
//        WorkoutsRepositoryImpl(
//            api           = get(),              // WorkoutApi
//            dao           = get(),              // WorkoutDao
//            ioDispatcher  = get(named("IO"))    // io-диспетчер из appModule
//        )
//    }

    //Фейковый репозиторий
    single<WorkoutsRepository> {
        FakeWorkoutsRepository()
    }

    viewModel {
        WorkoutsViewModel(
            repo          = get(),              // WorkoutsRepository
            ioDispatcher  = get(named("IO"))    // io-диспетчер
        )
    }
}