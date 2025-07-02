package com.example.feature_nutrition.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [MealEntryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(LocalDateTimeConverter::class)  // чтобы Room знал, как сохранять LocalDateTime
abstract class NutritionDatabase : RoomDatabase() {

    abstract fun mealEntryDao(): MealEntryDao
}