package com.example.petcare_app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.petcare_app.data.model.Schedule
import com.example.petcare_app.data.viewmodel.SchedulesScreenViewModel
import com.example.petcare_app.datastore.TokenDataStore
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.components.agendamentosComponents.AgendamentoCard
import com.example.petcare_app.ui.components.agendamentosComponents.AgendamentoItem
import com.example.petcare_app.ui.components.agendamentosComponents.NenhumAgendamentoCard
import com.example.petcare_app.ui.components.buttons.FilterChip
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable
import com.example.petcare_app.ui.components.layouts.LoadingBar
import com.example.petcare_app.ui.components.layouts.WhiteCanvas
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle
import java.time.LocalDateTime

@OptIn(ExperimentalLayoutApi::class)
@SuppressLint("NewApi")
@Composable
fun SchedulesScreen(navController: NavController) {
    val context = LocalContext.current
    val dataStore = TokenDataStore.getInstance(context)

    val token by dataStore.getToken.collectAsState(initial = null)
    val id by dataStore.getId.collectAsState(initial = null)

    val viewModel: SchedulesScreenViewModel = viewModel()

    LaunchedEffect(token, id) {
        if (token != null && id != null) {
            viewModel.getAllSchedulesByUser(token!!, id!!)
            viewModel.getAllPetsByUserId(token!!, id!!)
        }
    }

    val nomesDosPets by viewModel.allPetsUser.collectAsState()
    val filtros = listOf("Todos os pets") + nomesDosPets.map { it.name }
    var filtroSelecionado by remember { mutableStateOf("Todos os pets") }

    val agendamentos by viewModel.allSchedulesByUser.collectAsState()
    val agendamentosFiltrados = if (filtroSelecionado == "Todos os pets") agendamentos
                                else agendamentos.filter { it.petName == filtroSelecionado }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(topBar = {
            HeaderComposable(
                navController
            )
        }, bottomBar = { GadjetBarComposable(navController) }) { it ->
            Column(Modifier.background(Color(0, 84, 114)).padding(it)) {
                WhiteCanvas(
                    modifier = Modifier.fillMaxHeight(),
                    icon = Icons.Filled.WatchLater,
                    "Agendamentos",
                    navController = navController
                ) {
                    if (viewModel.isLoading) {
                        LoadingBar()
                    } else {
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

                        LazyColumn() {
                            if (agendamentosFiltrados.size > 0) {
                                items(agendamentosFiltrados) { schedule ->
                                    AgendamentoCard(
                                        AgendamentoItem(
                                            id = schedule.id ?: 0,
                                            scheduleDate = LocalDateTime.parse(schedule.scheduleDate),
                                            servicos = schedule.serviceNames.map { it },
                                            statusPagamento = schedule.paymentStatus == "APPROVED",
                                            statusAgendamento = schedule.scheduleStatus,
                                            nomePet = schedule.petName,
                                            review = schedule.review
                                        ),
                                        navController = navController,
                                        onAvaliar = { token, idAgendamento, nota ->
                                            viewModel.reviewScheduleByID(
                                                token,
                                                idAgendamento,
                                                nota,
                                                id!!
                                            )
                                        },
                                        token = token
                                    )
                                }
                            }
                            else if (!viewModel.isLoading) {
                                item {
                                    NenhumAgendamentoCard()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}