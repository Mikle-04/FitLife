package com.example.feature_nutrition.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface MealEntryDao {
    @Query("SELECT * FROM meal_entries WHERE date(timestamp/86400,'unixepoch') = date(:date/86400,'unixepoch')")
    fun getByDate(date: LocalDate): Flow<List<MealEntryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(entry: MealEntryEntity)

    @Query("DELETE FROM meal_entries WHERE id = :id")
    suspend fun deleteById(id: String)
}