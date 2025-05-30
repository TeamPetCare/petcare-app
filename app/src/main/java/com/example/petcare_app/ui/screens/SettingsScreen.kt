package com.example.petcare_app.ui.screens

import TokenDataStore
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petcare_app.R
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.components.buttons.LogoutButton
import com.example.petcare_app.ui.components.layouts.LoadingBar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject

@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val tokenDataStore: TokenDataStore = koinInject()


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderComposable(navController)

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clickable { navController.navigate(Screen.EditUser.route) }
        ) {
            Text("Editar Usuário")
        }

        // Botão de Sair
        LogoutButton(navController, tokenDataStore)

        // BottomBar
        GadjetBarComposable(
            navController = navController
        )
    }
}

