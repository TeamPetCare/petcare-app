
package com.example.petcare_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.R
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable
import com.example.petcare_app.ui.components.layouts.StatusAgendamento
import com.example.petcare_app.ui.components.layouts.StatusComposable
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.montserratFontFamily
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle
import com.example.petcare_app.ui.theme.paragraphTextStyle
import androidx.compose.material.icons.filled.Check

@Composable
fun ScheduleDetailsScreen(navController: NavController, scheduleId: Int? = null) {
    // Dados mockados para demonstração - depois serão substituídos pelos dados reais do BD
    val mockScheduleData = ScheduleDetailData(
        id = "001",
        title = "Banho e Tosa",
        status = "AGENDADO",
        date = "17 Jan 2025",
        timeRange = "10:00-11:00",
        address = "Avenida Inocêncio Seráfico",
        petName = "Rex",
        paymentMethod = "Pix",
        isPaid = true,
        description = "Ainda não há detalhes sobre este atendimento.",
        services = listOf(
            ServiceItem("Banho", 20.00),
            ServiceItem("Tosa", 40.00)
        ),
        photos = listOf(
            R.drawable.pets_welcome, // Usando imagens do projeto
            R.drawable.pet_hand_high_five
        )
    )

    Scaffold(
        topBar = {
            HeaderComposable(navController)
        },
        bottomBar = {
            GadjetBarComposable(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF005472))
                .padding(paddingValues)
        ) {
            // Conteúdo principal com fundo branco arredondado
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
                            text = "${mockScheduleData.title} #${mockScheduleData.id}",
                            style = sentenceTitleTextStyle,
                            color = customColorScheme.primary,
                            modifier = Modifier.weight(1f)
                        )

                        StatusAgendamento(status = mockScheduleData.status)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Data e hora
                    Text(
                        text = "${mockScheduleData.date} | ${mockScheduleData.timeRange}",
                        fontSize = 16.sp,
                        color = customColorScheme.primary,
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Endereço
                    Text(
                        text = "Endereço",
                        fontSize = 14.sp,
                        color = customColorScheme.primary,
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = mockScheduleData.address,
                        fontSize = 14.sp,
                        color = customColorScheme.primary,
                        fontFamily = montserratFontFamily
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Pet e Pagamento
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = "Pet",
                                fontSize = 14.sp,
                                color = customColorScheme.primary,
                                fontFamily = montserratFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = mockScheduleData.petName,
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
                                color = customColorScheme.primary,
                                fontFamily = montserratFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = mockScheduleData.paymentMethod,
                                fontSize = 14.sp,
                                color = customColorScheme.primary,
                                fontFamily = montserratFontFamily
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Spacer(modifier = Modifier.height(18.dp)) // Alinha com o texto do pagamento
                            StatusComposable(
                                icon = Icons.Default.Check,
                                status = "Pago",
                                fontColor = Color.White,
                                backgroundColor = Color(0xFF2EC114),
                                textColor = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Descrição
                    Text(
                        text = "Descrição",
                        fontSize = 14.sp,
                        color = customColorScheme.primary,
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = mockScheduleData.description,
                        fontSize = 14.sp,
                        color = customColorScheme.primary,
                        fontFamily = montserratFontFamily
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Serviços
                    Text(
                        text = "Serviços",
                        fontSize = 14.sp,
                        color = customColorScheme.primary,
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    mockScheduleData.services.forEach { service ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = service.name,
                                fontSize = 16.sp,
                                color = customColorScheme.primary,
                                fontFamily = montserratFontFamily
                            )
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
                            color = customColorScheme.primary,
                            fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "R$${String.format("%.2f", mockScheduleData.services.sumOf { it.price })}",
                            fontSize = 16.sp,
                            color = customColorScheme.primary,
                            fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botão Baixar Comprovante
                    Button(
                        onClick = {
                            // Ação para baixar comprovante
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(38.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = customColorScheme.primary,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(18.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Baixar comprovante",
                                fontSize = 16.sp,
                                fontFamily = montserratFontFamily,
                                fontWeight = FontWeight.SemiBold
                            )
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = "Baixar",
                                tint = Color.White
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    // Fotos
                    Text(
                        text = "Fotos",
                        fontSize = 14.sp,
                        color = customColorScheme.primary,
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(mockScheduleData.photos) { photo ->
                            Image(
                                painter = painterResource(id = photo),
                                contentDescription = "Foto do atendimento",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))
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
    ScheduleDetailsScreen(navController, 1)
}