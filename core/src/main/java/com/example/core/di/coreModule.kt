package com.example.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

val coreModule = module{

    single<DataStore<Preferences>> {
        PreferenceDataStoreFactory.create(
            produceFile = {
                File(get<Context>().filesDir, "datastore/preferences.pb")
            }
        )
    }


    single {
        Retrofit.Builder()
            .baseUrl("https://api.yourserver.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}