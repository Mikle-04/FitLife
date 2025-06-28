package com.example.fitlifecoach

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.feature_auth.presentation.navigation.AuthRoutes
import com.example.feature_auth.presentation.navigation.authGraph
import com.example.fitlifecoach.ui.theme.FitLifeCoachTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FitLifeCoachTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = AuthRoutes.Login
                ) {
                    authGraph(
                        navController,
                        onAuthSuccess ={ navController.navigate("workouts/list") {
                            popUpTo(AuthRoutes.Login) { inclusive = true }
                        }})
                        //  workoutsGraph(navController)
                    //nutritionGraph(navController)
                }
            }
        }
    }
}

