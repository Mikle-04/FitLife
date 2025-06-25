package com.example.feature_auth.presentation

import com.example.core.presentatin.mvi.MviReducer

object AuthReducer : MviReducer<AuthState, AuthAction> {
    override fun reduce(state: AuthState, action: AuthAction): AuthState = when(action) {
        AuthAction.TokenLoading -> state.copy(isLoading = true, error = null)
        is AuthAction.TokenLoaded -> state.copy(isLoading = false, token = action.token)

        AuthAction.LoginStart -> state.copy(isLoading = true, error = null)
        is AuthAction.LoginSuccess -> state.copy(isLoading = false, token = action.token)
        is AuthAction.LoginError -> state.copy(isLoading = false, error = action.message)

        AuthAction.RegisterStart -> state.copy(isLoading = true, error = null)
        is AuthAction.RegisterSuccess -> state.copy(isLoading = false, token = action.token)
        is AuthAction.RegisterError -> state.copy(isLoading = false, error = action.message)

        AuthAction.RestoreStart -> state.copy(isLoading = true, error = null, restoreSent = false)
        AuthAction.RestoreSuccess -> state.copy(isLoading = false, restoreSent = true)
        is AuthAction.RestoreError -> state.copy(isLoading = false, error = action.message)

        AuthAction.LogoutComplete -> state.copy(token = null)
    }
}