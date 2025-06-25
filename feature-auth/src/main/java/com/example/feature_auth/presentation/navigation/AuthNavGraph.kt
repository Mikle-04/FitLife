package com.example.feature_auth.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.feature_auth.ui.Screens.LoginScreen
import com.example.feature_auth.ui.Screens.RegisterScreen
import com.example.feature_auth.ui.Screens.RestoreScreen
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.authGraph(
    navController: NavHostController,
    onAuthSuccess: () -> Unit
) {
    composable(AuthRoutes.Login) {
        LoginScreen(
            navController = navController,
            viewModel = koinViewModel(),
            onLoginSuccess = onAuthSuccess
        )
    }
    composable(AuthRoutes.Register) {
        RegisterScreen(
            navController = navController,
            viewModel = koinViewModel(),
            onRegisterSuccess = onAuthSuccess
        )
    }
    composable(AuthRoutes.Restore) {
        RestoreScreen(
            navController = navController,
            viewModel = koinViewModel(),
            onRestoreSent = { /* может показать тост */ }
        )
    }
}