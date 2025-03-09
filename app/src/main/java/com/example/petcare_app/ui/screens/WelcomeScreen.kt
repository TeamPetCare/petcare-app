package com.example.petcare_app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petcare_app.R
import com.example.petcare_app.navigation.Screen

@Composable
fun WelcomeScreen(navController: NavController) {
    // Cores
    val primaryBlue = Color(0xFF005472)
    val lightYellow = Color(0xFFFFEABA)
    val lightBlue = Color(0xFFB8E0E9)
    val darkBlue = Color(0xFF00677F)

    // Estado do CPF
    var cpf by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            // Imagem de pets
            Image(
                painter = painterResource(id = R.drawable.pets_welcome),
                contentDescription = "Cachorro e gato com balão de coração",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Título principal
            Text(
                text = "Bem-vindo(a)!",
                color = primaryBlue,
                fontSize = 32.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_extrabold)),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtítulo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Queremos garantir que é você mesmo!\nInsira seu CPF para te identificarmos ",
                    color = primaryBlue,
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.montserrat)),
                    textAlign = TextAlign.Center
                )

                // Emoji
                Text(
                    text = "",
                    fontSize = 10.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de CPF
            OutlinedTextField(
                value = cpf,
                onValueChange = {
                    // Limitado a 14 caracteres (CPF formatado)
                    if (it.length <= 14) cpf = it
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("___.___.___-__", color = Color.LightGray) },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = primaryBlue,
                    unfocusedBorderColor = Color.LightGray,
                    cursorColor = primaryBlue
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botão Verificar
            Button(
                onClick = { /* Verificar CPF */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = lightYellow,
                    contentColor = darkBlue
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Verificar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.montserrat)),
                        modifier = Modifier.align(Alignment.Center)
                    )

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Próximo",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp)
                            .size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto informativo
            Text(
                text = "Seu CPF é usado apenas para\nlocalizar sua conta. Não se preocupe,\nseus dados estão seguros.",
                color = primaryBlue,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
    }
}

// Função para formatar CPF enquanto o usuário digita
fun formatCpf(cpf: String): String {
    val digitsOnly = cpf.filter { it.isDigit() }

    return when {
        digitsOnly.length <= 3 -> digitsOnly
        digitsOnly.length <= 6 -> "${digitsOnly.substring(0, 3)}.${digitsOnly.substring(3)}"
        digitsOnly.length <= 9 -> "${digitsOnly.substring(0, 3)}.${digitsOnly.substring(3, 6)}.${digitsOnly.substring(6)}"
        else -> "${digitsOnly.substring(0, 3)}.${digitsOnly.substring(3, 6)}.${digitsOnly.substring(6, 9)}-${digitsOnly.substring(9, minOf(11, digitsOnly.length))}"
    }
}