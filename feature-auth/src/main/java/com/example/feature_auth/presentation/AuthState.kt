package com.example.feature_auth.presentation

import com.example.core.presentatin.mvi.MviState

data class AuthState(
    val isLoading: Boolean = false,    // показываем спиннер?
    val token: String? = null,         // если не null — залогинены
    val error: String? = null,         // текст ошибки
    val restoreSent: Boolean = false   // восстановление прошло
): MviState
