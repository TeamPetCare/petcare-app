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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.data.viewmodel.SchedulesHomeAppViewModel
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
import com.example.petcare_app.ui.components.plansComponents.PlanCard
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.paragraphTextStyle
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@SuppressLint("NewApi")
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreenApp(navController: NavController) {
    val context = LocalContext.current
    val dataStore = TokenDataStore.getInstance(context)

    val token by dataStore.getToken.collectAsState(initial = null)
    val id by dataStore.getId.collectAsState(initial = null)
    val dateNow = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS)

    val viewModel: SchedulesHomeAppViewModel = viewModel()

    LaunchedEffect(token, id) {
        if (token != null && id != null) {
            viewModel.getAllSchedulesMonthByUser(token!!, id!!, dateNow)
            viewModel.getAllPetsByUserId(token!!, id!!)
        }
    }

    val nomesDosPets by viewModel.allPetsUser.collectAsState()
    val filtros = listOf("Todos os pets") + nomesDosPets.map { it.name }
    var filtroSelecionado by remember { mutableStateOf("Todos os pets") }

    val agendamentos by viewModel.allSchedulesMonth.collectAsState()
    val agendamentosFiltrados = if (filtroSelecionado == "Todos os pets") agendamentos
                                else agendamentos.filter { it.pet.name == filtroSelecionado }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(topBar = {
            HeaderComposable(
                navController
            )
        }, bottomBar = { GadjetBarComposable(navController) })

        { it ->
            Column(Modifier.background(Color(0, 84, 114)).padding(it)) {
                if (viewModel.isLoading) {
                    LoadingBar()
                } else {
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
                            if (agendamentosFiltrados.size > 0) {
                                items(agendamentosFiltrados) { schedule ->
                                    AgendamentoCard(
                                        AgendamentoItem(
                                            dataHoraAgendamento = LocalDateTime.parse(schedule.scheduleDate),
                                            servicos = schedule.services.mapNotNull { it.name },
                                            statusPagamento = schedule.payment?.paymentStatus == "PAGO",
                                            statusAgendamento = schedule.scheduleStatus,
                                            nomePet = schedule.pet.name
                                        )
                                    )
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
                                                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
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
                            } else {
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

@Preview(showBackground = true)
@Composable
fun HomeScreenAppPreview() {
    val navController = rememberNavController()

    HomeScreenApp(
        navController = navController
    )
}