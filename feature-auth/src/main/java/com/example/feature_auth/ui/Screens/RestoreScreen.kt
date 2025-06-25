package com.example.feature_auth.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.feature_auth.presentation.AuthIntent
import com.example.feature_auth.presentation.AuthViewModel
import com.example.feature_auth.presentation.navigation.AuthRoutes
import org.koin.androidx.compose.koinViewModel

@Composable
fun RestoreScreen(
    navController: NavHostController,
    viewModel: AuthViewModel = koinViewModel(),
    onRestoreSent: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    var email by remember { mutableStateOf("") }

    // Как только восстановление успешно — вызываем коллбэк
    LaunchedEffect(state.restoreSent) {
        if (state.restoreSent) {
            onRestoreSent()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text("Восстановление пароля", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = {
                viewModel.sendIntent(AuthIntent.Restore(email))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Отправить письмо")
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
            Text("Вернуться к входу")
        }
        TextButton(onClick = { navController.navigate(AuthRoutes.Register) }) {
            Text("Нет аккаунта? Зарегистрироваться")
        }
    }
}