package com.example.feature_nutrition.di

import androidx.room.Room
import com.example.feature_nutrition.data.db.MealEntryDao
import com.example.feature_nutrition.data.db.NutritionDatabase
import com.example.feature_nutrition.data.remote.NutritionApi
import com.example.feature_nutrition.data.repository.NutritionRepositoryImpl
import com.example.feature_nutrition.domain.models.repository.NutritionRepository
import com.example.feature_nutrition.presentation.NutritionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val nutritionModule = module {

    single<NutritionApi> {
        get<Retrofit>().create(NutritionApi::class.java)
    }

    single {
        Room.databaseBuilder(
            get(),
            NutritionDatabase::class.java,
            "nutrition_db"
        ).build()
    }

    single<MealEntryDao> {
        get<NutritionDatabase>().mealEntryDao()
    }

    single<NutritionRepository> {
        NutritionRepositoryImpl(
            api          = get(),
            dao          = get(),
            ioDispatcher = get(named("IO"))
        )
    }

    // 5) MVI-ViewModel для экрана питания
    viewModel {
        NutritionViewModel(
            repo         = get(),
            ioDispatcher = get(named("IO"))
        )
    }
}