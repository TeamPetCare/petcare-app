package com.example.petcare_app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petcare_app.ui.screens.HomeScreen
import com.example.petcare_app.ui.screens.PlansScreen
import com.example.petcare_app.ui.screens.SignupScreen


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Signup : Screen("signup")
    object Plans : Screen("plans")
}

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Signup.route) { SignupScreen(navController) }
        composable(Screen.Plans.route) { PlansScreen(navController) }
    }
}