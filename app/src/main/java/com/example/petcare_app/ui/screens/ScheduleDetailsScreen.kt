package com.example.petcare_app.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable
import com.example.petcare_app.ui.components.layouts.StatusAgendamento
import com.example.petcare_app.ui.components.layouts.StatusComposable
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.montserratFontFamily
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petcare_app.data.viewmodel.ScheduleDetailsViewModel
import com.example.petcare_app.datastore.TokenDataStore
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.components.agendamentosComponents.CancelarAgendamento
import com.example.petcare_app.ui.components.agendamentosComponents.LinkPagamento
import com.example.petcare_app.ui.components.dialogs.ConfirmationDialog
import com.example.petcare_app.ui.components.layouts.LoadingBar
import com.example.petcare_app.utils.DataUtils.calcularHorarioFinal
import com.example.petcare_app.utils.DataUtils.formatarDataHora
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime

@SuppressLint("NewApi")
@Composable
fun ScheduleDetailsScreen(
    navController: NavController,
    scheduleId: Int
) {
    val scheduleDetailsViewModel: ScheduleDetailsViewModel = viewModel()
    val schedulesInfo by scheduleDetailsViewModel.scheduleInfo.collectAsState()

    val context = LocalContext.current
    val dataStore = TokenDataStore.getInstance(context)
    val token by dataStore.getToken.collectAsState(initial = null)

    var showCancelarDialog by remember { mutableStateOf(false) }

    LaunchedEffect(token, scheduleId) {
        if (token != null && scheduleId != null) {
            scheduleDetailsViewModel.getScheduleInfoByID(token!!, scheduleId!!)
        }
    }

    val servicosFormatados = when (schedulesInfo?.services?.size ?: 0) {
        0 -> ""
        1 -> schedulesInfo?.services?.get(0)?.name ?: ""
        2 -> "${schedulesInfo?.services?.get(0)?.name} e ${schedulesInfo?.services?.get(1)?.name}"
        else -> {
            val inicio = schedulesInfo?.services?.dropLast(1)?.map { it.name }?.joinToString(", ") ?: ""
            val ultimo = schedulesInfo?.services?.lastOrNull()?.name ?: ""
            "$inicio e $ultimo"
        }
    }

    val dataHora = schedulesInfo?.scheduleDate?.let {
        try { formatarDataHora(LocalDateTime.parse(it)) } catch (_: Exception) { "Sem data/hora" }
    } ?: ""

    val horaFinal = if (schedulesInfo?.scheduleDate != null && schedulesInfo?.scheduleTime != null)
        calcularHorarioFinal(schedulesInfo?.scheduleDate!!, schedulesInfo?.scheduleTime!!)
    else "Sem hora final"

    val totalPreco = schedulesInfo?.services?.sumOf { it.price ?: 0.0 } ?: 0.0

    // Componente de confirmação de cancelar agendamento
    if (showCancelarDialog) {
        ConfirmationDialog(
            title = "Deseja cancelar o agendamento?",
            onConfirm = {
                scheduleDetailsViewModel.cancelSchedule(
                    token!!,
                    schedulesInfo?.id!!
                )
                showCancelarDialog = false
            },
            onDismiss = { showCancelarDialog = false }
        )
    }


    Scaffold(
        topBar = {
            HeaderComposable(navController)
        },
        bottomBar = {
            GadjetBarComposable(navController)
        }
    ) { paddingValues ->
        if (scheduleDetailsViewModel.isLoading) {
            LoadingBar()
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF005472))
                    .padding(paddingValues)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 0.dp),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        // Header com botão fechar e título
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = { navController.popBackStack() },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Fechar",
                                    tint = customColorScheme.primary
                                )
                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = "${servicosFormatados} #${scheduleId}",
                                style = sentenceTitleTextStyle,
                                color = customColorScheme.primary,
                                modifier = Modifier.weight(1f)
                            )



                            schedulesInfo?.scheduleStatus?.let { StatusAgendamento(status = it) }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Data e hora
                        Text(
                            text = "${dataHora} - ${horaFinal}",
                            fontSize = 16.sp,
                            color = customColorScheme.primary,
                            fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.Medium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Pet e Pagamento
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Pet",
                                    fontSize = 14.sp,
                                    color = Color(0xFF707070),
                                    fontFamily = montserratFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = schedulesInfo?.petName ?: "Sem pet",
                                    fontSize = 14.sp,
                                    color = customColorScheme.primary,
                                    fontFamily = montserratFontFamily
                                )
                            }

                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = "Pagamento",
                                    fontSize = 14.sp,
                                    color = Color(0xFF707070),
                                    fontFamily = montserratFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                                schedulesInfo?.paymentMethod?.let {
                                    Text(
                                        text = it,
                                        fontSize = 14.sp,
                                        color = customColorScheme.primary,
                                        fontFamily = montserratFontFamily
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.End
                            ) {
                                StatusComposable(
                                    icon = if (schedulesInfo?.paymentStatus == "APPROVED") Icons.Default.Check else Icons.Default.Close,
                                    status = if (schedulesInfo?.paymentStatus == "APPROVED") "Pago" else "Não Pago",
                                    fontColor = if (schedulesInfo?.paymentStatus == "APPROVED") Color(0xFF2EC114) else Color(0xFFC11414),
                                    backgroundColor = if (schedulesInfo?.paymentStatus == "APPROVED") Color(0xFF2EC114) else Color(0xFFC11414),
                                    textColor = Color.White
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Descrição
                        Text(
                            text = "Descrição",
                            fontSize = 14.sp,
                            color = Color(0xFF707070),
                            fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = schedulesInfo?.scheduleNote ?: "",
                            fontSize = 14.sp,
                            color = customColorScheme.primary,
                            fontFamily = montserratFontFamily
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Serviços
                        Text(
                            text = "Serviços",
                            fontSize = 14.sp,
                            color = Color(0xFF707070),
                            fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        schedulesInfo?.services?.forEach { service ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                service?.name?.let {
                                    Text(
                                        text = it,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = customColorScheme.primary,
                                        fontFamily = montserratFontFamily
                                    )
                                }
                                Text(
                                    text = "R$${String.format("%.2f", service.price)}",
                                    fontSize = 16.sp,
                                    color = customColorScheme.primary,
                                    fontFamily = montserratFontFamily
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        // Total
                        Divider(color = Color.LightGray, thickness = 1.dp)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Total",
                                fontSize = 16.sp,
                                color = Color(0xFF707070),
                                fontFamily = montserratFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "R$${String.format("%.2f", totalPreco)}",
                                fontSize = 16.sp,
                                color = customColorScheme.primary,
                                fontFamily = montserratFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (schedulesInfo?.scheduleStatus == "AGENDADO") {
                            CancelarAgendamento(
                                cancelScheduleFunction = {
                                    showCancelarDialog = true
                                }
                            )
                        }

                        if (schedulesInfo?.scheduleStatus == "AGENDADO" && schedulesInfo?.paymentStatus == "PENDING" && schedulesInfo?.paymentMethod == "PIX") {
                            LinkPagamento(
                                linkPagamentoFunction = {
                                    schedulesInfo?.paymentLink
                                },
                                context = context
                            )
                        }

                        // Botão Baixar Comprovante
//                    Button(
//                        onClick = {
//                            // Ação para baixar comprovante
//                        },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(38.dp),
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = customColorScheme.primary,
//                            contentColor = Color.White
//                        ),
//                        shape = RoundedCornerShape(18.dp)
//                    ) {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.SpaceBetween,
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Text(
//                                text = "Baixar comprovante",
//                                fontSize = 16.sp,
//                                fontFamily = montserratFontFamily,
//                                fontWeight = FontWeight.SemiBold
//                            )
//                            Icon(
//                                imageVector = Icons.Default.KeyboardArrowRight,
//                                contentDescription = "Baixar",
//                                tint = Color.White
//                            )
//                        }
//                    }

                        Spacer(modifier = Modifier.height(24.dp))

//                    // Fotos
//                    Text(
//                        text = "Fotos",
//                        fontSize = 14.sp,
//                        color = Color(0xFF707070),
//                        fontFamily = montserratFontFamily,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    Spacer(modifier = Modifier.height(8.dp))
//
//                    LazyRow(
//                        horizontalArrangement = Arrangement.spacedBy(8.dp)
//                    ) {
//                        items(mockScheduleData.photos) { photo ->
//                            Image(
//                                painter = painterResource(id = photo),
//                                contentDescription = "Foto do atendimento",
//                                modifier = Modifier
//                                    .size(120.dp)
//                                    .clip(RoundedCornerShape(8.dp)),
//                                contentScale = ContentScale.Crop
//                            )
//                        }
//                    }

                    }
                }
            }
        }
    }
}

// Modelos de dados
data class ScheduleDetailData(
    val id: String,
    val title: String,
    val status: String,
    val date: String,
    val timeRange: String,
    val address: String,
    val petName: String,
    val paymentMethod: String,
    val isPaid: Boolean,
    val description: String,
    val services: List<ServiceItem>,
    val photos: List<Int> // Para recursos drawable, depois será List<String> para URLs
)

data class ServiceItem(
    val name: String,
    val price: Double
)

@Preview(showBackground = true)
@Composable
fun ScheduleDetailsScreenPreview() {
    val navController = rememberNavController()
//    ScheduleDetailsScreen(navController, 1)
}