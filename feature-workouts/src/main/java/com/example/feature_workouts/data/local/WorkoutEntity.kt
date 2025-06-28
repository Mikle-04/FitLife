package com.example.feature_workouts.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.feature_workouts.data.remote.WorkoutDto
import com.example.feature_workouts.domain.models.Workout

@Entity(tableName = "workouts")
data class WorkoutEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val videoUrl: String
)

fun WorkoutDto.toEntity() = WorkoutEntity(id, title, description, videoUrl)
fun WorkoutEntity.toModel() = Workout(id, title, description, videoUrl)
