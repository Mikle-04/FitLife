package com.example.feature_auth.presentation

import com.example.core.presentatin.mvi.MviIntent

sealed interface AuthIntent : MviIntent {
    object LoadToken : AuthIntent   // при старте проверим, сохранён ли токен
    data class Login(val email: String, val password: String) : AuthIntent  // юзер нажал «Войти»
    data class Register(val name: String, val email: String, val password: String) : AuthIntent  // юзер нажал «Зарегистрироваться»
    data class Restore(val email: String) : AuthIntent    // юзер нажал «Восстановить пароль»
    object Logout : AuthIntent   // юзер вышел из акка
}