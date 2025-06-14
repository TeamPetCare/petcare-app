package com.example.petcare_app.ui.components.agendamentosComponents

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.petcare_app.data.dto.ScheduleDTO
import com.example.petcare_app.data.dto.SchedulePUTDTO
import com.example.petcare_app.data.model.Schedule
import com.example.petcare_app.data.viewmodel.ScheduleDetailsViewModel
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.components.avaliacaoComponents.AvaliarAgendamento
import com.example.petcare_app.ui.components.layouts.StatusAgendamento
import com.example.petcare_app.ui.components.layouts.StatusComposable
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.montserratFontFamily
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle
import com.example.petcare_app.utils.DataUtils.formatarDataHora
import com.google.gson.Gson
import java.time.LocalDateTime

@Composable
fun AgendamentoCard(
    agendamento: AgendamentoItem,
    navController: NavController? = null,
    onAvaliar: (token: String, id: Int, review: Int) -> Unit,
    token: String? = null
) {
    val servicosFormatados = when (agendamento.servicos?.size) {
        0 -> ""
        1 -> agendamento.servicos[0]
        2 -> "${agendamento.servicos[0]} e ${agendamento.servicos[1]}"
        else -> {
            val inicio = agendamento.servicos?.dropLast(1)?.joinToString(", ")
            val ultimo = agendamento.servicos?.last()
            "$inicio e $ultimo"
        }
    }

    val currentRoute = navController?.currentBackStackEntry?.destination?.route

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 8.dp)
            .clickable {
                navController?.navigate(Screen.ScheduleDetails.createRoute(agendamento.id))
            },
        colors = CardDefaults.cardColors(containerColor = if (agendamento.statusAgendamento == "AGENDADO" || agendamento.statusAgendamento == "CONCLUIDO") customColorScheme.onSecondaryContainer else Color(0xFFF0F0F0)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${formatarDataHora(agendamento.scheduleDate)} | ${agendamento.nomePet}",
                            fontSize = 14.sp,
                            color = customColorScheme.primary,
                            fontFamily = montserratFontFamily
                        )

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Seta para Direita",
                            tint = customColorScheme.primary,
                            modifier = Modifier
                                .clickable {
                                    navController?.navigate(Screen.ScheduleDetails.createRoute(agendamento.id)) {
                                        currentRoute?.let {
                                            popUpTo(it) {
                                                inclusive = false
                                                saveState = true
                                            }
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                        )
                    }

                    Row {
                        if (servicosFormatados != null) {
                            Text(
                                text = servicosFormatados,
                                style = sentenceTitleTextStyle,
                                fontSize = 22.sp,
                                color = customColorScheme.primary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row {
                            StatusComposable(
                                icon = if (agendamento.statusPagamento == true) Icons.Default.Check else Icons.Default.Close,
                                status = if (agendamento.statusPagamento == true) "Pago" else "Não Pago",
                                fontColor = if (agendamento.statusPagamento == true) Color(0xFF2EC114) else Color(0xFFC11414),
                                backgroundColor = if (agendamento.statusPagamento == true) Color(0xFF2EC114) else Color(0xFFC11414),
                                textColor = Color.White
                            )
                            Spacer(modifier = Modifier.width(7.dp))
                            StatusAgendamento(
                                status = agendamento.statusAgendamento
                            )
                        }

                        if (agendamento.statusAgendamento == "CONCLUIDO" && agendamento.statusPagamento) {
                            AvaliarAgendamento(
                                jaAvaliado = agendamento.review != null,
                                avaliacao = agendamento.review ?: 0,
                                agendamento = agendamento,
                                onAvaliar = { _, nota ->
                                    onAvaliar(token!!, agendamento.id, nota)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

data class AgendamentoItem (
    val id: Int,
    val scheduleDate: LocalDateTime,
    val servicos: List<String?>?,
    val statusPagamento: Boolean,
    val statusAgendamento: String,
    val nomePet: String,
    val review: Int? = null
)

//@SuppressLint("NewApi")
//@Preview
//@Composable
//fun AgendamentoCardPreview() {
//    val agendamentos = listOf(
//        AgendamentoItem(
//            id = 1,
//            dataHoraAgendamento = LocalDateTime.of(2025, 4, 20, 14, 0),
//            servicos = listOf("Banho", "Tosa", "Consulta"),
//            statusPagamento = true,
//            statusAgendamento = "CONCLUIDO",
//            nomePet = "Toto",
//            review = null
//        )
//    )
//
//    LazyColumn {
//        items(agendamentos) { agendamento ->
//            AgendamentoCard(agendamento)
//        }
//    }
//}