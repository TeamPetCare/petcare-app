package com.example.petcare_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.components.CustomTextInput

@Composable
fun SignupScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var nomeCompleto = remember { mutableStateOf("") }
        var senha = remember { mutableStateOf("") }

        Text(text = "Compartilhe um pouco sobre você!", style = MaterialTheme.typography.headlineLarge)
        Text(text = "Essas informações ajudam a personalizar sua experiência no app.", style = MaterialTheme.typography.bodyMedium)
        Text(text = "* Campos obrigatórios.", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))
        CustomTextInput(
            value = nomeCompleto.value,
            onValueChange = { nomeCompleto.value = it },
            label = "Nome Completo",
            modifier = Modifier.fillMaxWidth()
        )

        Button(onClick = { navController.navigate(Screen.Home.route) }) {
            Text("Voltar tela inicial")
        }
    }
}
