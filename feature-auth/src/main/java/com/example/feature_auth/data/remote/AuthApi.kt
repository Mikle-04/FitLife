package com.example.feature_auth.data.remote

import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("login")
    suspend fun login(@Body req: LoginRequest): AuthResponse

    @POST("register")
    suspend fun register(@Body req: RegisterRequest) : AuthResponse

    @POST("password/restore")
    suspend fun restorePassword(@Body req: RestoreRequest): AuthResponse
}

data class LoginRequest(val email: String, val password: String)
data class RegisterRequest(val name: String, val email: String, val password: String)
data class RestoreRequest(val email: String)
data class AuthResponse(val token: String)