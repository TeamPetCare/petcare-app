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
import com.example.petcare_app.ui.screens.HomeScreen
import com.example.petcare_app.ui.screens.LoadingScreen
import com.example.petcare_app.ui.screens.LoginScreen
import com.example.petcare_app.ui.screens.SignUpPetScreen
import com.example.petcare_app.ui.screens.SignUpUserScreen
import com.example.petcare_app.ui.screens.WelcomeScreen


sealed class Screen(val route: String) {
    object Home : Screen("home")
    object SignUpUser : Screen("signupuser")
    object SignUpPet : Screen("signuppet")
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object Loading : Screen("loading")
}

@Composable
fun NavGraph(navController: NavHostController) {
    val signUpUserViewModel: SignUpViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.SignUpUser.route) { SignUpUserScreen(navController, signUpUserViewModel) }
        composable(Screen.SignUpPet.route) { SignUpPetScreen(navController, signUpUserViewModel) }
        composable(Screen.Welcome.route) { WelcomeScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Loading.route) { LoadingScreen(navController, Screen.Home.route) }
    }
}

class SignUpViewModel : ViewModel() {
    var nomeCompleto by mutableStateOf("")
    var cpf by mutableStateOf("")
    var email by mutableStateOf("")
    var celular by mutableStateOf("")
    var senha by mutableStateOf("") //Lembrar de encriptografar
    var confirmarSenha by mutableStateOf("")
    var cep by mutableStateOf("")
    var logradouro by mutableStateOf("")
    var bairro by mutableStateOf("")
    var complemento by mutableStateOf("")
    var cidade by mutableStateOf("")
}