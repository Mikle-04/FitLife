package com.example.feature_auth.presentation

import androidx.lifecycle.viewModelScope
import com.example.core.presentatin.BaseMviViewModel
import com.example.feature_auth.domain.models.AuthResult
import com.example.feature_auth.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository,
    private val mainDispatcher: CoroutineDispatcher
): BaseMviViewModel<AuthIntent, AuthState, AuthAction>(
    initialState = AuthState(),
    reducer = AuthReducer
){
    override fun handleIntent(intent: AuthIntent) {
        when (intent) {
            AuthIntent.LoadToken -> loadToken()
            is AuthIntent.Login -> login(intent.email, intent.password)
            is AuthIntent.Register -> register(intent.name, intent.email, intent.password)
            is AuthIntent.Restore -> restore(intent.email)
            AuthIntent.Logout -> logout()
        }
    }

    private fun loadToken() {
        dispatch(AuthAction.TokenLoading)
        repository.tokenFlow()
            .onEach { token ->
                dispatch(AuthAction.TokenLoaded(token))
            }
            .launchIn(viewModelScope)
    }

    private fun login(email: String, password: String) {
        dispatch(AuthAction.LoginStart)
        viewModelScope.launch {
            when(val res = repository.login(email, password)) {
                is AuthResult.Success -> dispatch(AuthAction.LoginSuccess(res.token))
                is AuthResult.Error   -> dispatch(AuthAction.LoginError(res.message))
            }
        }
    }

    private fun register(name: String, email: String, password: String) {
        dispatch(AuthAction.RegisterStart)
        viewModelScope.launch {
            when(val res = repository.register(name, email, password)) {
                is AuthResult.Success -> dispatch(AuthAction.RegisterSuccess(res.token))
                is AuthResult.Error   -> dispatch(AuthAction.RegisterError(res.message))
            }
        }
    }

    private fun restore(email: String) {
        dispatch(AuthAction.RestoreStart)
        viewModelScope.launch {
            when(val res = repository.restorePassword(email)) {
                is AuthResult.Success -> dispatch(AuthAction.RestoreSuccess)
                is AuthResult.Error   -> dispatch(AuthAction.RestoreError(res.message))
            }
        }
    }

    private fun logout() {
        viewModelScope.launch {
            repository.logout()
            dispatch(AuthAction.LogoutComplete)
        }
    }

}