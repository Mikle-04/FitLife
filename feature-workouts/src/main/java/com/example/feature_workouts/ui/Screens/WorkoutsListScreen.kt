package com.example.feature_workouts.ui.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.feature_workouts.presentation.WorkoutsViewModel
import org.koin.androidx.compose.koinViewModel
import com.example.feature_workouts.presentation.mvi.WorkoutsIntent
import com.example.feature_workouts.presentation.navigation.WorkoutsRoutes

@Composable
fun WorkoutsListScreen(
    navController: NavHostController,
    viewModel: WorkoutsViewModel = koinViewModel()
) {
    // Подписываемся на состояние из ViewModel
    val state by viewModel.state.collectAsState()

    // При первом отображении экрана шлём Intent загрузить список
    LaunchedEffect(Unit) {
        viewModel.sendIntent(WorkoutsIntent.LoadList)
    }

    // Корневая разметка
    Box(modifier = Modifier.fillMaxSize()) {
        // Список тренировок
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            items(state.list) { workout ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable {
                            // Навигация на экран деталей
                            navController.navigate(WorkoutsRoutes.detailRoute(workout.id))
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = workout.title, style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = workout.description, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        // Индикатор загрузки
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Ошибка
        state.error?.let { errorMsg ->
            Text(
                text = errorMsg,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    }
}