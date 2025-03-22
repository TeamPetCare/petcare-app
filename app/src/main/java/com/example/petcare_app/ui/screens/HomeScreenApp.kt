package com.example.petcare_app.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable

@Composable
fun HomeScreenApp(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize() // Ocupa toda a tela
    ) {
        // Header no topo
        HeaderComposable(userName = "Usuário")

        // Área de conteúdo (ocupa todo o espaço disponível)
        Box(
            modifier = Modifier
                .weight(1f) // Faz com que o conteúdo ocupe o máximo de espaço
                .fillMaxWidth()
        ) {
            Text("TELA APP HOME")
            // Aqui você pode adicionar a lista de planos, textos, botões etc.
        }

        // Barra de navegação fixa na parte inferior
        GadjetBarComposable(
            navController = navController,
            criarAgendamento = {
                println("Criar agendamento pelo HomeScreenApp")
            }
        )
    }
}