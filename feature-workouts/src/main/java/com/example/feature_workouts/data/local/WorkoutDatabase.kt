package com.example.feature_workouts.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WorkoutEntity::class], version = 1, exportSchema = false)
abstract class WorkoutDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
}