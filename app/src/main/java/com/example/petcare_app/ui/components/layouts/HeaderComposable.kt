package com.example.petcare_app.ui.components.layouts

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.petcare_app.R

@Composable
fun HeaderComposable(userName: String, profileImageUrl: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF005472))
            .padding(16.dp)
    ) {

        // Saudação com Foto e Notificação
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                AsyncImage(
                    Uri.parse(profileImageUrl),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(48.dp)
                        .border(2.dp, Color.White, shape = CircleShape)
                        .padding(2.dp),
                    placeholder = painterResource(id = R.drawable.ic_launcher_foreground),
                    error = painterResource(id = R.drawable.ic_launcher_foreground)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Olá, $userName!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            // Sino de Notificação ajustado corretamente
            IconButton(
                onClick = { /* Ação de notificação */ },
                modifier = Modifier.offset(x = 12.dp) // Ajuste fino para alinhar com a bateria
            ) {
                Icon(
                    imageVector = Icons.Outlined.Notifications,
                    tint = Color.White,
                    contentDescription = "Notificações"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPreview() {
    HeaderComposable(
        userName = "Usuário",
        profileImageUrl = "https://placekitten.com/200/200" // Imagem de teste
    )
}