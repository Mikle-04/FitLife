package com.example.fitlifecoach

import android.app.Application
import com.example.core.di.coreModule
import com.example.feature_auth.di.authModule
import com.example.feature_nutrition.di.nutritionModule
import com.example.feature_workouts.di.workoutsModule
import com.example.fitlifecoach.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class FitLifeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@FitLifeApplication)
            modules(listOf(coreModule, authModule, workoutsModule, nutritionModule, appModule))

        }
    }
}