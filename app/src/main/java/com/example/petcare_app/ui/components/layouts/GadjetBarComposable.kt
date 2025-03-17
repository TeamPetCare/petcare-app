package com.example.petcare_app.ui.components.layouts

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.petcare_app.R
import androidx.compose.ui.graphics.Color
import com.example.petcare_app.ui.theme.DarkBlue

@Composable
fun GadjetBarComposable() {

    NavigationBar (
        containerColor = Color.White, // Cor de fundo da barra (opcional)
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = DarkBlue) },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_timer_clock),
                    contentDescription = "Clock",
                    tint = DarkBlue,
                    modifier = Modifier.size(22.dp) // Aumenta o tamanho do ícone
                )
            },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plus),
                    contentDescription = "Plus",
                    tint = DarkBlue,
                    modifier = Modifier.size(24.dp) // Aumenta o tamanho do ícone
                )
            },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_plans),
                    contentDescription = "Plans",
                    tint = DarkBlue,
                    modifier = Modifier.size(18.dp) // Aumenta o tamanho do ícone
                )
            },
            selected = false,
            onClick = {}
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Configurações", tint = DarkBlue) },
            selected = false,
            onClick = {}
        )
    }

}

@Preview(showBackground = true)
@Composable
fun GadgetsPreview() {
    GadjetBarComposable()
}