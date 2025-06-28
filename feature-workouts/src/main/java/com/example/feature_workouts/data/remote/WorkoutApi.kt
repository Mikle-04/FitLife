package com.example.feature_workouts.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface WorkoutApi {
    @GET("workouts")
    suspend fun fetchAll(): List<WorkoutDto>

    @GET("workouts/{id}")
    suspend fun fetchById(@Path("id") id: String): WorkoutDto
}

data class WorkoutDto(
    val id: String,
    val title: String,
    val description: String,
    val videoUrl: String
)