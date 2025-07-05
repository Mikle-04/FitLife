package com.example.feature_auth.data.repository

import com.example.feature_auth.domain.models.AuthResult
import com.example.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeAuthRepository: AuthRepository {
    private val _token = MutableStateFlow<String?>(null)

    override fun tokenFlow(): Flow<String?> = _token.asStateFlow()

    override suspend fun login(
        email: String,
        password: String
    ): AuthResult {
        delay(300)  // эмулируем сеть
        val token = "fake-token-${email.hashCode()}"
        _token.value = token
        return AuthResult.Success(token)
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String
    ): AuthResult {
        delay(400)
        val token = "fake-token-${name.hashCode()}"
        _token.value = token
        return AuthResult.Success(token)
    }

    override suspend fun restorePassword(email: String): AuthResult {
        delay(200)
        return AuthResult.Success("") // письмо якобы ушло
    }


    override suspend fun logout() {
        _token.value = null
    }
}