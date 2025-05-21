package com.example.petcare_app.ui.components.agendamentosComponents

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
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
import com.example.petcare_app.ui.components.layouts.StatusAgendamento
import com.example.petcare_app.ui.components.layouts.StatusComposable
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.montserratFontFamily
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle
import com.example.petcare_app.utils.DataUtils.formatarDataHora
import java.time.LocalDateTime

@Composable
fun AgendamentoCard(agendamento: AgendamentoItem) {
    val servicosFormatados = when (agendamento.servicos.size) {
        0 -> ""
        1 -> agendamento.servicos[0]
        2 -> "${agendamento.servicos[0]} e ${agendamento.servicos[1]}"
        else -> {
            val inicio = agendamento.servicos.dropLast(1).joinToString(", ")
            val ultimo = agendamento.servicos.last()
            "$inicio e $ultimo"
        }
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
//            .height(100.dp)
            .padding(bottom = 8.dp),
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
                            text = "${formatarDataHora(agendamento.dataHoraAgendamento)} | ${agendamento.nomePet}",
                            fontSize = 14.sp,
                            color = customColorScheme.primary,
                            fontFamily = montserratFontFamily
                        )

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Seta para Direita",
                            tint = customColorScheme.primary,
                            modifier = Modifier
                                .clickable {  }
                        )
                    }

                    Row {
                        Text(
                            text = servicosFormatados,
                            style = sentenceTitleTextStyle,
                            fontSize = 22.sp,
                            color = customColorScheme.primary
                        )
                    }

                    Spacer(modifier = Modifier.height(3.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
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
                                jaAvaliado = agendamento.avaliacao != null,
                                avaliacao = agendamento.avaliacao ?: 0
                            )
                        }
                    }
                }
            }
        }
    }
}

data class AgendamentoItem (
    val dataHoraAgendamento: LocalDateTime,
    val servicos: List<String>,
    val statusPagamento: Boolean,
    val statusAgendamento: String,
    val avaliacao: Int? = null,
    val nomePet: String
)

@SuppressLint("NewApi")
@Preview
@Composable
fun AgendamentoCardPreview() {
    val agendamentos = listOf(
        AgendamentoItem(
            dataHoraAgendamento = LocalDateTime.of(2025, 4, 20, 14, 0),
            servicos = listOf("Banho", "Tosa", "Consulta"),
            statusPagamento = true,
            statusAgendamento = "AGENDADO",
            nomePet = "Toto"
        )
    )

    LazyColumn {
        items(agendamentos) { agendamento ->
            AgendamentoCard(agendamento)
        }
    }
}