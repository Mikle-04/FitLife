package com.example.feature_nutrition.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.feature_nutrition.domain.models.MealEntry
import java.time.LocalDateTime

@Entity(tableName = "meal_entries")
@TypeConverters(LocalDateTimeConverter::class)
data class MealEntryEntity(
    @PrimaryKey val id: String,
    val recipeId: String?,
    val customName: String?,
    val calories: Int,
    val protein: Int,
    val fat: Int,
    val carbs: Int,
    val timestamp: LocalDateTime
)

// Конвертер для Room
class LocalDateTimeConverter {
    @androidx.room.TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? =
        value?.let { LocalDateTime.ofEpochSecond(it, 0, java.time.ZoneOffset.UTC) }
    @androidx.room.TypeConverter
    fun toTimestamp(date: LocalDateTime?): Long? =
        date?.toEpochSecond(java.time.ZoneOffset.UTC)
}

fun MealEntryEntity.toModel() = MealEntry(
    id = id,
    recipeId = recipeId,
    customName = customName,
    calories = calories,
    protein = protein,
    fat = fat,
    carbs = carbs,
    timestamp = timestamp
)
fun MealEntry.toEntity() = MealEntryEntity(
    id = id,
    recipeId = recipeId,
    customName = customName,
    calories = calories,
    protein = protein,
    fat = fat,
    carbs = carbs,
    timestamp = timestamp
)