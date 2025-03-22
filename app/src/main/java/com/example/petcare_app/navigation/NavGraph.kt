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
    import com.example.petcare_app.ui.screens.HomeScreenApp
    import com.example.petcare_app.ui.screens.LoadingScreenToAppHome
    import com.example.petcare_app.ui.screens.LoadingScreenToSignUpUser
    import com.example.petcare_app.ui.screens.LoginScreen
    import com.example.petcare_app.ui.screens.PlansScreen
    import com.example.petcare_app.ui.screens.SchedulesScreen
    import com.example.petcare_app.ui.screens.SettingsScreen
    import com.example.petcare_app.ui.screens.SignUpPetScreen
    import com.example.petcare_app.ui.screens.SignUpUserScreen
    import com.example.petcare_app.ui.screens.WelcomeScreen

    sealed class Screen(val route: String) {
        object Home : Screen("home")
        object HomeApp : Screen("homeapp")
        object SignUpUser : Screen("signupuser")
        object SignUpPet : Screen("signuppet")
        object Welcome : Screen("welcome")
        object Login : Screen("login")
        object LoadingToSignUpUser : Screen("loadingtosignupuser")
        object LoadingToAppHome : Screen("loadingtoapphome")
        object Plans : Screen("plans")
        object Schedules : Screen("schedules") // Tela de Agendamentos
        object Settings : Screen("settings") // Tela de Configurações
    }

    @Composable
    fun NavGraph(navController: NavHostController) {
        val signUpViewModel: SignUpViewModel = viewModel()

        NavHost(navController = navController, startDestination = Screen.HomeApp.route) {
            composable(Screen.Home.route) { HomeScreen(navController) }
            composable(Screen.HomeApp.route) { HomeScreenApp(navController) }
            composable(Screen.SignUpUser.route) { SignUpUserScreen(navController, signUpViewModel) }
            composable(Screen.SignUpPet.route) { SignUpPetScreen(navController, signUpViewModel) }
            composable(Screen.Welcome.route) { WelcomeScreen(navController) }
            composable(Screen.Login.route) { LoginScreen(navController) }
            composable(Screen.LoadingToSignUpUser.route) { LoadingScreenToSignUpUser(navController, Screen.SignUpUser.route) }
            composable(Screen.LoadingToAppHome.route) { LoadingScreenToAppHome(navController, Screen.HomeApp.route) }
            composable(Screen.Plans.route) { PlansScreen(navController) }
            composable(Screen.Schedules.route) { SchedulesScreen(navController) }
            composable(Screen.Settings.route) { SettingsScreen(navController) }
        }
    }