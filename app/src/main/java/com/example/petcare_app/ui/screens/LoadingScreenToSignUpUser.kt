package com.example.petcare_app.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petcare_app.R
import kotlinx.coroutines.delay

@Composable
fun LoadingScreenToSignUpUser(navController: NavController, nextScreen: String = "signupuser") {
    // Cores
    val primaryBlue = Color(0xFF005472)

    // Estado para controlar os pontos animados
    var dotCount by remember { mutableStateOf(0) }

    // Animação de pulsação para a imagem
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = EaseInOutCubic),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    // Efeito para animar os pontos
    LaunchedEffect(key1 = true) {
        while (true) {
            delay(500)
            dotCount = (dotCount + 1) % 4
        }
    }

    // Navegar após 3 segundos
    LaunchedEffect(key1 = true) {
        delay(3000)
        navController.navigate(nextScreen) {
            popUpTo(0) // Limpa o backstack
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Imagem dos pets com animação
            Image(
                painter = painterResource(id = R.drawable.pets_loading),
                contentDescription = "Pets esperando",
                modifier = Modifier
                    .width(300.dp)
                    .height(280.dp)
                    .scale(scale),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Texto com pontos animados
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Patinhas carregando",
                    color = primaryBlue,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.montserrat)),
                    textAlign = TextAlign.Center
                )

                // Pontos animados
                Text(
                    text = ".".repeat(dotCount),
                    color = primaryBlue,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.montserrat))
                )

                // Pegada de patinhas
                Text(
                    text = " 🐾",
                    fontSize = 20.sp
                )
            }
        }
    }
}