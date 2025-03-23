package com.example.petcare_app.ui.components.layouts

import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
//import coil.compose.AsyncImage
import com.example.petcare_app.R
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.theme.montserratFontFamily

@Composable
//fun HeaderComposable(userName: String, profileImageUrl: String) {
fun HeaderComposable(navController: NavController, userName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF005472))
            .padding(vertical = 10.dp, horizontal = 16.dp)
    ) {

        // Saudação com Foto e Notificação
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
//                    Uri.parse(profileImageUrl),
                    painter = painterResource(id = R.drawable.ic_foto_mockado_perfil),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(48.dp)
//                        .border(2.dp, Color.White, shape = CircleShape)
                        .padding(2.dp),
//                    placeholder = painterResource(id = R.drawable.ic_no_profile_picture),
//                    error = painterResource(id = R.drawable.ic_no_profile_picture)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Olá, $userName!",
                    fontFamily = montserratFontFamily,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            }

            // Sino de Notificação ajustado corretamente
            IconButton(
                onClick = { navController.navigate(Screen.Notifications.route) },
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
    val navController = rememberNavController()

    HeaderComposable(
        userName = "Usuário",
        navController = navController
//        profileImageUrl = "https://placekitten.com/200/200" // Imagem de teste
    )
}