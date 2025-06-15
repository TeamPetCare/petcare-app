package com.example.petcare_app.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.petcare_app.data.viewmodel.SignUpViewModel
import com.example.petcare_app.ui.screens.*

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object HomeApp : Screen("homeapp")
    object SignUpUser : Screen("signupuser")
    object SignUpPet : Screen("signuppet")
    object Welcome : Screen("welcome")
    object Login : Screen("login")
    object LoadingToSignUpUser : Screen("loadingtosignupuser")
    object LoadingToAppHome : Screen("loadingtoapphome")
    object Plans : Screen("plans")
    object Schedules : Screen("schedules")
    object Settings : Screen("settings")
    object Notifications : Screen("notifications")
    object EditUser : Screen("editUser")
    object PetRegister : Screen("pet_register")
    object ScheduleDetails : Screen("schedule_details/{scheduleId}") {
        fun createRoute(scheduleId: Int) = "schedule_details/$scheduleId"
    }
}

@Composable
fun NavGraph(navController: NavHostController) {
    val signUpViewModel: SignUpViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.HomeApp.route) { HomeScreenApp(navController) }
        composable(Screen.SignUpUser.route) { SignUpUserScreen(navController, signUpViewModel) }
        composable(Screen.SignUpPet.route) { SignUpPetScreen(navController, signUpViewModel) }
        composable(Screen.Welcome.route) { WelcomeScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.LoadingToSignUpUser.route) {
            LoadingScreenToSignUpUser(navController, Screen.SignUpUser.route)
        }
        composable(Screen.LoadingToAppHome.route) {
            LoadingScreenToAppHome(navController, Screen.HomeApp.route)
        }
        composable(Screen.Plans.route) { PlansScreen(navController) }
        composable(Screen.Schedules.route) { SchedulesScreen(navController) }
        composable(Screen.Settings.route) { SettingsScreen(navController) }
        composable(Screen.Notifications.route) { NotificationScren(navController) }
        composable(Screen.EditUser.route) { EditUserScreen(navController, signUpViewModel) }
        composable(Screen.PetRegister.route) { PetRegisterScreen(navController) }
        composable("schedule_details/{scheduleId}") { backStackEntry ->
            val scheduleId = backStackEntry.arguments?.getString("scheduleId")?.toIntOrNull()
            ScheduleDetailsScreen(navController, scheduleId)
        }
    }
}
