package com.example.feature_workouts.presentation.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.feature_workouts.presentation.WorkoutsViewModel
import com.example.feature_workouts.ui.Screens.WorkoutDetailScreen
import com.example.feature_workouts.ui.Screens.WorkoutsListScreen
import org.koin.androidx.compose.koinViewModel

/** Расширение для подключения графа тренировок в NavHost */
fun NavGraphBuilder.workoutsGraph(
    navController: NavHostController
) {
    // 1) Список тренировок
    composable(WorkoutsRoutes.List) {
        val vm: WorkoutsViewModel = koinViewModel()
        WorkoutsListScreen(
            navController = navController,
            viewModel     = vm
        )
    }

    // 2) Детали конкретной тренировки по workoutId
    composable(
        route = WorkoutsRoutes.Detail,
        arguments = listOf(
            navArgument("workoutId") {
                type = androidx.navigation.NavType.StringType
            }
        )
    ) { backStackEntry ->
        val workoutId = backStackEntry.arguments?.getString("workoutId")!!
        val vm: WorkoutsViewModel = koinViewModel()
        WorkoutDetailScreen(
            navController = navController,
            viewModel     = vm,
            workoutId     = workoutId
        )
    }
}