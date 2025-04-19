package com.example.petcare_app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.SupervisedUserCircle
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.components.agendamentosComponents.AgendamentoCard
import com.example.petcare_app.ui.components.agendamentosComponents.AgendamentoItem
import com.example.petcare_app.ui.components.buttons.FilterChip
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable
import com.example.petcare_app.ui.components.layouts.WhiteCanvas
import com.example.petcare_app.ui.components.plansComponents.PlanCard
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.paragraphTextStyle
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle
import java.time.LocalDateTime

@SuppressLint("NewApi")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreenApp(navController: NavController) {
    val nomesDosPets = listOf("Luna", "Thor", "Madonna")
    val filtros = listOf("Todos os pets") + nomesDosPets

    // No ViewModel
    // val pets = mutableStateListOf<Pet>()
    //
    // val filtros: List<String>
    //    get() = listOf("Todos os pets") + pets.map { it.nome }
    // No composable
    // val filtros = vm.filtros

    var filtroSelecionado by remember { mutableStateOf("Todos os pets") }

//    val listaFiltrada = when (filtroSelecionado) {
//        "Thor" -> pets.filter { it.nome == "Thor" }
//        "Madonna" -> pets.filter { it.nome == "Madonna" }
//        else -> pets
//    }

    val agendamentos = listOf(
        AgendamentoItem(
            dataHoraAgendamento = LocalDateTime.of(2025, 4, 20, 14, 0),
            servicos = listOf("Banho", "Tosa", "Consulta"),
            statusPagamento = true,
            statusAgendamento = "AGENDADO"
        ),
        AgendamentoItem(
            dataHoraAgendamento = LocalDateTime.of(2025, 4, 18, 9, 30),
            servicos = listOf("Vacinação"),
            statusPagamento = true,
            statusAgendamento = "CONCLUIDO"
        ),
        AgendamentoItem(
            dataHoraAgendamento = LocalDateTime.of(2025, 4, 20, 9, 30),
            servicos = listOf("Banho", "Tosa"),
            statusPagamento = true,
            statusAgendamento = "CONCLUIDO",
            avaliacao = 4
        ),
        AgendamentoItem(
            dataHoraAgendamento = LocalDateTime.of(2025, 4, 17, 11, 0),
            servicos = listOf("Consulta de rotina"),
            statusPagamento = false,
            statusAgendamento = "CANCELADO"
        ),
        AgendamentoItem(
            dataHoraAgendamento = LocalDateTime.of(2025, 5, 25, 13, 0),
            servicos = listOf("Banho"),
            statusPagamento = false,
            statusAgendamento = "CANCELADO"
        ),
        AgendamentoItem(
            dataHoraAgendamento = LocalDateTime.of(2025, 5, 25, 13, 0),
            servicos = listOf("Banho"),
            statusPagamento = false,
            statusAgendamento = "CANCELADO"
        ),
        AgendamentoItem(
            dataHoraAgendamento = LocalDateTime.of(2025, 5, 25, 13, 0),
            servicos = listOf("Banho"),
            statusPagamento = false,
            statusAgendamento = "CANCELADO"
        )


    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(topBar = {
            HeaderComposable(
                navController,
                userName = "Usuário"
            )
        }, bottomBar = { GadjetBarComposable(navController) }) { it ->
            Column(Modifier.background(Color(0, 84, 114)).padding(it)) {
                WhiteCanvas(
                    modifier = Modifier.fillMaxHeight(),
                    icon = Icons.Filled.WatchLater,
                    "Agendamentos do mês",
                    navController = navController
                ) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        filtros.forEach { filtro ->
                            FilterChip(
                                text = filtro,
                                isSelected = filtroSelecionado == filtro,
                                onClick = { filtroSelecionado = filtro }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    LazyColumn () {
                        items(agendamentos) { agendamento ->
                            AgendamentoCard(agendamento)
                        }

                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                Text(
                                    text = "Visualizar todos os agendamentos >",
                                    modifier = Modifier
                                        .clickable {
                                            navController.navigate(Screen.Schedules.route) {
                                                popUpTo(navController.graph.startDestinationId) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        },
                                    color = customColorScheme.primary,
                                    style = sentenceTitleTextStyle,
                                    fontSize = 15.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenAppPreview() {
    val navController = rememberNavController()

    HomeScreenApp(
        navController = navController
    )
}