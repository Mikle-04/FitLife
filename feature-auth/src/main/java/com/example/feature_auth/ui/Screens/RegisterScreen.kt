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
fun RegisterScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = koinViewModel(),
    onRegisterSuccess: () -> Unit
) {
    // Подписываемся на стейт из VM
    val state by viewModel.state.collectAsState()

    // Локальные поля формы
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Когда получаем токен — вызываем onRegisterSuccess
    LaunchedEffect(state.token) {
        if (state.token != null) {
            onRegisterSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Имя") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
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
            label = { Text("Пароль") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.sendIntent(AuthIntent.Register(name, email, password))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Зарегистрироваться")
        }

        Spacer(Modifier.height(8.dp))
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }
        state.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(Modifier.height(16.dp))
        TextButton(onClick = { navController.navigate(AuthRoutes.Login) }) {
            Text("Уже есть аккаунт? Войти")
        }
        TextButton(onClick = { navController.navigate(AuthRoutes.Restore) }) {
            Text("Забыли пароль?")
        }
    }
}