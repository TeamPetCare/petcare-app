    package com.example.petcare_app.ui.components.notificationComponents

    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.filled.Close
    import androidx.compose.material3.Card
    import androidx.compose.material3.CardDefaults
    import androidx.compose.material3.Icon
    import androidx.compose.material3.IconButton
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import com.example.petcare_app.R
    import com.example.petcare_app.R.drawable.ic_check
    import com.example.petcare_app.ui.theme.customColorScheme
    import com.example.petcare_app.ui.theme.montserratFontFamily
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
                .fillMaxWidth() // Mantém o Card com largura total
//                .height(100.dp) // Define a altura fixa do Card
                .padding(bottom = 8.dp), // Espaçamento inferior
            colors = CardDefaults.cardColors(containerColor = notification.backgroundColor),
            shape = RoundedCornerShape(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp), // Adiciona padding ao redor do conteúdo
                contentAlignment = Alignment.Center // Centraliza o conteúdo no Card
            ) {
                // Ícone de Fechar no topo direito
                IconButton(
                    onClick = { /* Ação ao fechar a notificação */ },
                    modifier = Modifier
                        .size(28.dp)
                        .align(Alignment.TopEnd) // Coloca o botão de fechar no canto superior direito
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Fechar notificação",
                        tint = if (notification.iconType == "pix") customColorScheme.primary else Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(end = 40.dp),
                    verticalAlignment = Alignment.Top
                ) {
                    // Ícone da notificação
                    Box(
                        modifier = Modifier
                            .padding(end = 10.dp, top = 10.dp)
                            .size(40.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        val (icon, iconDescription) = when (notification.iconType) {
                            "check" -> Pair(ic_check, "Ícone de sucesso")
                            "bell" -> Pair(R.drawable.ic_bell, "Ícone de sino")
                            "pix" -> Pair(R.drawable.ic_pix, "Ícone de pix")
                            else -> Pair(R.drawable.ic_error, "Ícone de erro")
                        }

                        Icon(
                            modifier = Modifier
                                .size(30.dp),
                            painter = painterResource(id = icon),
                            contentDescription = iconDescription,
                            tint = notification.fontColor,
                        )
                    }

                    // Texto da notificação
                    Column(
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = notification.title,
                            style = sentenceTitleTextStyle,
                            fontSize = 17.sp,
                            color = notification.fontColor
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            lineHeight = 15.sp,
                            text = notification.description,
                            fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp,
                            color = notification.fontColor
                        )
                    }
                }
            }
        }
    }
