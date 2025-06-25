package com.example.feature_auth.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenStorage(private val ds: DataStore<Preferences>) {
    private val TOKEN_KEY = stringPreferencesKey("auth_token")

    suspend fun saveToken(token: String) {
        ds.edit { prefs ->
            prefs[TOKEN_KEY] = token
        }
    }

    suspend fun clearToken() {
        ds.edit { prefs ->
            prefs.remove(TOKEN_KEY)
        }
    }

    fun tokenFlow(): Flow<String?> = ds.data.map { prefs -> prefs[TOKEN_KEY] }
}