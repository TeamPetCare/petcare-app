package com.example.petcare_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.R
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable
import com.example.petcare_app.ui.components.layouts.WhiteCanvas
import com.example.petcare_app.ui.components.notificationComponents.NotificationCard
import com.example.petcare_app.ui.components.notificationComponents.NotificationItem


@Preview
@Composable
private fun NotificationScreenPreview() {
    val navController = rememberNavController()
    NotificationScren(navController)
}

@Composable
fun NotificationScren(navController: NavController){

    val notifications = listOf(
        NotificationItem("Agendamento Confirmado.", "Atendimento agendado para 23/05 às 17:57.", Color.White ,
            Color(0, 131, 55), "check"),
        NotificationItem("Agendamento Próximo!", "O Rex tem um agendamento hoje às 18:04.", Color.White ,
            Color(0, 84, 114), "bell"),
        NotificationItem("Pagamento Enviado.", "Pagamento de R\$50 foi enviado com sucesso.", Color(0, 84, 114) ,
            Color(255, 210, 105), "pix"),
        NotificationItem("Agendamento Confirmado.", "Atendimento agendado para 23/05 às 17:57.", Color.White ,
            Color(206, 0, 0), "error"),
    )

    Scaffold(
        topBar = {
            HeaderComposable(
                navController,
                userName = "Usuário"
            )
        },
        bottomBar = { GadjetBarComposable(navController, criarAgendamento = {})}
    ) { it ->
        Column(Modifier.background(Color(0, 84, 114)).padding(it)) {
            WhiteCanvas(
                modifier = Modifier.fillMaxHeight(),
                icon = ImageVector.vectorResource(R.drawable.ic_x),
                iconWeight = 20f,
                title = "Notificações",
                navController = navController
            ) {
                LazyColumn {
                    items(notifications){ notification ->
                        NotificationCard(notification)
                    }
                }
            }
        }
    }


}