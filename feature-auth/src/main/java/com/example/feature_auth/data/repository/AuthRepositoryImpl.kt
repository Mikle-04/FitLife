package com.example.feature_auth.data.repository

import com.example.feature_auth.data.local.TokenStorage
import com.example.feature_auth.data.remote.AuthApi
import com.example.feature_auth.data.remote.LoginRequest
import com.example.feature_auth.data.remote.RegisterRequest
import com.example.feature_auth.data.remote.RestoreRequest
import com.example.feature_auth.domain.models.AuthResult
import com.example.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val api: AuthApi,
    private val storage: TokenStorage,
    private val ioDispatcher: CoroutineDispatcher
): AuthRepository {
    override suspend fun login(email: String, password: String): AuthResult =
        withContext(ioDispatcher){
            try {
                val resp = api.login(LoginRequest(email, password))
                storage.saveToken(resp.token)
                AuthResult.Success(resp.token)
            }catch (e: Exception){
                AuthResult.Error(e.localizedMessage ?: "Unknown error")
            }
        }

    override suspend fun register(name: String, email: String, password: String): AuthResult =
        withContext(ioDispatcher) {
            try {
                val resp = api.register(RegisterRequest(name, email, password))
                storage.saveToken(resp.token)
                AuthResult.Success(resp.token)
            }catch (e : Exception){
                AuthResult.Error(e.localizedMessage ?: "Unknown error")
            }
        }

    override suspend fun restorePassword(email: String): AuthResult =
        withContext(ioDispatcher) {
        try {
            api.restorePassword(RestoreRequest(email))
            // можно просто отправить письмо, токена нет
            AuthResult.Success("")
        } catch (e: Exception) {
            AuthResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }
    override fun tokenFlow(): Flow<String?> = storage.tokenFlow()

    override suspend fun logout() = withContext(ioDispatcher) {
        storage.clearToken()
    }

}