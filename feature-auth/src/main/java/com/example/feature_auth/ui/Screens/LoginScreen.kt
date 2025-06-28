package com.example.feature_auth.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.feature_auth.presentation.AuthIntent
import com.example.feature_auth.presentation.AuthViewModel
import com.example.feature_auth.presentation.navigation.AuthRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit
) {
    // Подписываемся на StateFlow из ViewModel
    val state by viewModel.state.collectAsState()

    // Локальные переменные для формы
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Отреагировать на появление токена
    LaunchedEffect(state.token) {
        if (state.token != null) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                // шлём Intent в VM
                viewModel.sendIntent(AuthIntent.Login(email, password))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Войти")
        }
        Spacer(Modifier.height(8.dp))

        // Индикатор загрузки
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

        // Ошибка
        state.error?.let { Text(it, color = MaterialTheme.colorScheme.error) }

        // Ссылка на восстановление и регистрацию
        TextButton(onClick = { navController.navigate(AuthRoutes.Restore) }) {
            Text("Забыли пароль?")
        }
        TextButton(onClick = { navController.navigate(AuthRoutes.Register) }) {
            Text("Нет аккаунта? Зарегистрироваться")
        }
    }
}