package com.example.feature_auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.feature_auth.presentation.navigation.AuthRoutes
import com.example.feature_auth.presentation.navigation.authGraph
import com.example.feature_auth.ui.theme.FitLifeCoachTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitLifeCoachTheme {
                val navController = rememberNavController()

                Surface(color = MaterialTheme.colorScheme.background) {
                    NavHost(
                        navController = navController,
                        startDestination = AuthRoutes.Login
                    ) {

                        authGraph(
                            navController = navController,
                            onAuthSuccess = {
                                navController.navigate("workouts/list") {
                                    popUpTo(AuthRoutes.Login) { inclusive = true }
                                }
                            }
                        )

                        // Граф тренировок (feature-workouts)
                        //workoutsGraph(navController = navController)

                        // Граф питания (feature-nutrition)
                        // nutritionGraph(navController = navController)
                    }
                }
            }
        }
    }
}
