package com.example.petcare_app.ui.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.montserratFontFamily


@Composable
fun BackButtonToWelcome(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 0.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        Icon(
            modifier = Modifier
                .clickable { navController.popBackStack() },
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "Voltar",
            tint = customColorScheme.primary,
        )
        Text(
            modifier = Modifier
                .clickable { navController.navigate(Screen.Welcome.route) {
                    popUpTo(Screen.SignUpUser.route) { inclusive = true }
                    launchSingleTop = true
                } },
            text = "Voltar",
            textAlign = TextAlign.Start,
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = customColorScheme.primary
        )
    }
}