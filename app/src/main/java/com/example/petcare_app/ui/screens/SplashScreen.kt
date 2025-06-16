package com.example.petcare_app.ui.screens

import TokenDataStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.petcare_app.R
import com.example.petcare_app.data.viewmodel.VerifyTokenViewModel
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.theme.customColorScheme
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SplashScreen(navHostController: NavHostController) {
    val context = LocalContext.current
    val viewModel: VerifyTokenViewModel = koinViewModel()

    val dataStore: TokenDataStore = koinInject()

    val tokenFlow = remember { dataStore.getToken }
    val token by tokenFlow.collectAsState(initial = null)

    var isLoading by remember { mutableStateOf(true) }
    var hasNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(5000)
        isLoading = false

        Log.d("SplashScreen", "Token carregado: $token")
        if (token != null) {
            viewModel.verifyToken(token!!)
        } else {
            viewModel.isTokenValid = false
        }
    }

    LaunchedEffect(viewModel.isTokenValid) {
        if (!hasNavigated) {
            when (viewModel.isTokenValid) {
                true -> {
                    navHostController.navigate(Screen.HomeApp.route) {
                        popUpTo(navHostController.graph.startDestinationId) { inclusive = true }
                    }
                    hasNavigated = true
                }
                false -> {
                    navHostController.navigate(Screen.Welcome.route) {
                        popUpTo(navHostController.graph.startDestinationId) { inclusive = true }
                        launchSingleTop = true
                    }
                    hasNavigated = true
                }
                null -> {}
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(customColorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logo_petcare_svg),
            contentDescription = "Logo PetCare",
            modifier = Modifier.size(200.dp)
        )
    }
}
