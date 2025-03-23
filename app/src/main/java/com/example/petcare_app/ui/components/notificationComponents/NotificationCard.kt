package com.example.petcare_app.ui.components.notificationComponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.R
import com.example.petcare_app.R.drawable.ic_check
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle

@Preview
@Composable
fun NotificationCardPreview(){

    val notifications = listOf(
        NotificationItem("Agendamento Confirmado.", "Atendimento agendado para 23/05 às 17:57.", Color.White ,Color(0, 131, 55), "check"),
        NotificationItem("Agendamento Próximo!", "O Rex tem um agendamento hoje às 18:04.", Color.White ,Color(0, 84, 114), "bell"),
        NotificationItem("Pagamento Enviado.", "Pagamento de R\$50 foi enviado com sucesso.", Color(0, 84, 114) ,Color(255, 210, 105), "pix"),
        NotificationItem("Agendamento Confirmado.", "Atendimento agendado para 23/05 às 17:57.", Color.White ,Color(206, 0, 0), "error"),
    )

    LazyColumn {
        items(notifications) { notification ->
            NotificationCard(notification)
        }
    }
}

@Composable
fun NotificationCard(notification: NotificationItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = notification.backgroundColor)
    ) {
        Row {
            Box(modifier = Modifier
                .padding(start = 12.dp, top = 23.dp)){

                val (icon, iconDescription) = when (notification.iconType) {
                    "check" -> Pair(ic_check, "ícone de sucesso")
                    "bell" -> Pair(R.drawable.ic_bell, "Ícone de sino")
                    "pix" -> Pair(R.drawable.ic_pix, "Ícone de pix")
                    else -> Pair(R.drawable.ic_error, "Ícone de erro")
                }

                Icon(
                    modifier = Modifier.size(29.dp), // Aumenta o tamanho do ícone
                    painter = painterResource(id = icon),
                    contentDescription = iconDescription,
                    tint = notification.fontColor,
                )
            }

            Column(modifier = Modifier.padding(start = 12.dp, top = 24.dp,end = 24.dp, bottom = 24.dp)) {
                Text(
                    text = notification.title,
                    style = sentenceTitleTextStyle,
                    fontSize = 18.sp,
                    color = notification.fontColor
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = notification.description,
                    fontSize = 16.sp,
                    color = notification.fontColor
                )
            }
        }
    }
}
