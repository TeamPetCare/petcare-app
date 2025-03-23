package com.example.petcare_app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable

@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderComposable(navController, userName = "Usuário")

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Text("TELA AGENDAMENTOS")
        }

        // BottomBar
        GadjetBarComposable(
            navController = navController,
            criarAgendamento = {
                println("Criar agendamento pelo SettingsScreen")
            }
        )
    }
}