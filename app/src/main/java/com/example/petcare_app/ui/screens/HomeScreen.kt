package com.example.petcare_app.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petcare_app.R
import com.example.petcare_app.navigation.Screen

@Composable
fun HomeScreen(navController: NavController) {
    // Define colors
    val darkTeal = Color(0xFF005472)
    val yellow = Color(0xFFFFD166)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkTeal)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top bar with logo and login button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // PetCare logo
                Image(
                    painter = painterResource(id = R.drawable.logo_petcare),
                    contentDescription = "PetCare Logo",
                    modifier = Modifier.size(140.dp, 60.dp)
                )

                // Entrar button
                OutlinedButton(
                    onClick = { /* Navigate to login screen */ },
                    modifier = Modifier.height(30.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    ),
                    border = BorderStroke(width = 0.5.dp, color = Color.White)
                ) {
                    Text("Entrar", fontSize = 14.sp)
                }
            }

            // Espaçador para empurrar o conteúdo para baixo um pouco
            Spacer(modifier = Modifier.weight(1.5f))

            // Main content
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Main image card - Imagem maior agora
                Box(
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .width(290.dp)
                        .height(370.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(yellow)
                        .padding(7.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pet_hand_high_five),
                        contentDescription = "Pet high-fiving human hand",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                // Main text
                Text(
                    text = "Agende com ",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 0.dp)
                )

                Text(
                    text = "facilidade.",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 10.dp)
                )

                // Subtitle text
                Text(
                    text = "Serviços disponíveis para hoje ou amanhã.\nPara o seu pet, sempre que precisar!",
                    color = Color.White,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp,
                    modifier = Modifier.padding(horizontal = 32.dp)
                )

                // Pagination dots
                Row(
                    modifier = Modifier.padding(vertical = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    repeat(3) { index ->
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(if (index == 0) Color.White else Color.White.copy(alpha = 0.5f))
                        )
                    }
                }
            }

            // Espaçador principal
            Spacer(modifier = Modifier.weight(0.4f))

            // Começar button
            Button(
                onClick = { navController.navigate(Screen.SignUpUser.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = yellow,
                    contentColor = darkTeal
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                // Centraliza o texto "Começar" e coloca o ícone à direita
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Começar",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Arrow right",
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .padding(end = 8.dp)
                            .size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(26.dp))
        }
    }
}