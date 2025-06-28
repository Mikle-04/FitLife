package com.example.feature_auth.domain.repository

import com.example.feature_auth.domain.models.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    // POST /login
    suspend fun login(email: String, password: String): AuthResult

    // POST /register
    suspend fun register(name: String, email: String, password: String): AuthResult

    // POST /password/restore
    suspend fun restorePassword(email: String): AuthResult

    // Наблюдаем текущее состояние токена (для автоматического логина)
    fun tokenFlow(): Flow<String?>

    suspend fun logout()
}