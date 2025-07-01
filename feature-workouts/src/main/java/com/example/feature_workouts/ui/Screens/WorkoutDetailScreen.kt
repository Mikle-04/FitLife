package com.example.feature_workouts.ui.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.feature_workouts.presentation.WorkoutsViewModel
import org.koin.androidx.compose.koinViewModel
import com.example.feature_workouts.presentation.mvi.WorkoutsIntent

@Composable
fun WorkoutDetailScreen(
    navController: NavHostController,
    viewModel: WorkoutsViewModel = koinViewModel(),
    workoutId: String
) {
    // Подписываемся на состояние
    val state by viewModel.state.collectAsState()

    // При входе на экран шлём Intent выбрать конкретную тренировку
    LaunchedEffect(workoutId) {
        viewModel.sendIntent(WorkoutsIntent.Select(workoutId))
    }

    // Показываем загрузку, если нужно
    if (state.isLoading) {
        Box(Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }
        return
    }

    // Выбранная тренировка
    val workout = state.selected
    if (workout == null) {
        // Показываем сообщение, если ничего не пришло
        Box(Modifier.fillMaxSize()) {
            Text(
                text = "Тренировка не найдена",
                modifier = Modifier.align(Alignment.Center)
            )
        }
        return
    }

    // Детальный UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = workout.title, style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = workout.description, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        // Заглушка для видео-плеера:
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Video Player for ${workout.videoUrl}")
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = {
                // тут можно отправить Intent в ViewModel,
                // чтобы запланировать напоминание через WorkManager
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Напомнить о тренировке")
        }
    }
}