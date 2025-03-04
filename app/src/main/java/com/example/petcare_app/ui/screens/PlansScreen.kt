package com.example.petcare_app.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.ui.components.GadjetBarComposable
import com.example.petcare_app.ui.components.HeaderComposable
import com.example.petcare_app.ui.theme.PetCareAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetCareAppTheme {
                val navController = rememberNavController()
                PlansScreen(navController)
            }
        }
    }
}

@Composable
fun PlansScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize() // Ocupa toda a tela
    ) {
        // Header no topo
        HeaderComposable(userName = "Usuário", profileImageUrl = "https://placekitten.com/200/200")

        // Área de conteúdo (ocupa todo o espaço disponível)
        Box(
            modifier = Modifier
                .weight(1f) // Faz com que o conteúdo ocupe o máximo de espaço
                .fillMaxWidth()
        ) {
            // Aqui você pode adicionar a lista de planos, textos, botões etc.
        }

        // Barra de navegação fixa na parte inferior
        GadjetBarComposable()
    }
}

@Preview(showBackground = true)
@Composable
fun PlansScreenPreview() {
    val navController = rememberNavController()
    PlansScreen(navController)
}