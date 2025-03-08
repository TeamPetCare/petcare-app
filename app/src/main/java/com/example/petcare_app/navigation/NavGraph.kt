package com.example.petcare_app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petcare_app.data.viewmodel.SignUpViewModel
import com.example.petcare_app.ui.screens.HomeScreen
import com.example.petcare_app.ui.screens.SignUpPetScreen
import com.example.petcare_app.ui.screens.SignUpUserScreen


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object SignUpUser : Screen("signupuser")
    object SignUpPet : Screen("signuppet")
}

@Composable
fun NavGraph(navController: NavHostController) {
    val signUpViewModel: SignUpViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.SignUpPet.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.SignUpUser.route) { SignUpUserScreen(navController, signUpViewModel) }
        composable(Screen.SignUpPet.route) { SignUpPetScreen(navController, signUpViewModel) }
    }
}


