package com.example.feature_auth.presentation

import com.example.core.presentatin.mvi.MviAction

sealed interface AuthAction : MviAction{
    object TokenLoading : AuthAction                 // старт загрузки токена из стораджа
    data class TokenLoaded(val token: String?) : AuthAction  // получили/не получили токен

    object LoginStart : AuthAction                        // старт сети логина
    data class LoginSuccess(val token: String) : AuthAction // логин ок, есть токен
    data class LoginError(val message: String) : AuthAction  // логин упал

    object RegisterStart : AuthAction                // старт регистрации
    data class RegisterSuccess(val token: String) : AuthAction
    data class RegisterError(val message: String) : AuthAction

    object RestoreStart : AuthAction                 // старт восстановления
    object RestoreSuccess : AuthAction               // письмо ушло
    data class RestoreError(val message: String) : AuthAction

    object LogoutComplete : AuthAction               // токен очищен, выход завершён
}