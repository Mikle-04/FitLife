package com.example.feature_auth.domain.models

sealed class AuthResult {
    data class Success(val token : String): AuthResult()
    data class Error(val message : String): AuthResult()
}