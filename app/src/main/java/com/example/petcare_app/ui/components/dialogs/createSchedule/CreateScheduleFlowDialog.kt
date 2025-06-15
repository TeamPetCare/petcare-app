package com.example.petcare_app.ui.components.dialogs.createSchedule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petcare_app.data.viewmodel.CreateScheduleStep
import com.example.petcare_app.data.viewmodel.CreateScheduleViewModel
import com.example.petcare_app.datastore.TokenDataStore
import com.example.petcare_app.ui.theme.buttonTextStyle
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateScheduleFlowDialog(
    viewModel: CreateScheduleViewModel,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val dataStore = TokenDataStore.getInstance(context)
    val token by dataStore.getToken.collectAsState(initial = null)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                when (viewModel.currentStep) {
                    CreateScheduleStep.PAYMENT -> PaymentScreen(viewModel, token ?: "")
                    CreateScheduleStep.PIX_PAYMENT -> PixPaymentScreen(viewModel)
                    CreateScheduleStep.CASH_CONFIRMATION -> CashConfirmationScreen(viewModel)
                    CreateScheduleStep.SUCCESS -> SuccessScreen(viewModel, onDismiss)
                    else -> Unit
                }
            }
        }
    }
}

@Composable
private fun PaymentScreen(viewModel: CreateScheduleViewModel, token: String) {
    val formData = viewModel.currentFormData ?: return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        // Header com botão voltar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.previousStep() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = customColorScheme.primary
                )
            }
            Text(
                text = "Quase lá! Agora só falta o pagamento.",
                style = sentenceTitleTextStyle,
                color = customColorScheme.primary,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Resumo do agendamento
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = customColorScheme.primary.copy(alpha = 0.1f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Resumo do Agendamento",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = customColorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("Pet: ${formData.pet.name}")
                Text("Data: ${formData.date} às ${formData.time}")
                Text("Serviços: ${formData.services.joinToString { it.name }}")
                formData.employee?.let {
                    Text("Profissional: ${it.name}")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Total: R$ ${formData.totalPrice}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = customColorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Método de pagamento
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = CardDefaults.outlinedCardBorder()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.Payment,
                    contentDescription = null,
                    tint = customColorScheme.primary,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Forma de Pagamento: ${formData.paymentMethod.displayName}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botão para criar agendamento
        Button(
            onClick = { 
                viewModel.clearError()
                viewModel.createSchedule(token) 
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = customColorScheme.primary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = !viewModel.isLoading
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Confirmar Agendamento",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = buttonTextStyle
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Próximo"
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ConfirmationScreen(viewModel: CreateScheduleViewModel, token: String) {
    val formData = viewModel.currentFormData ?: return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        // Header com botão voltar
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.previousStep() }) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = customColorScheme.primary
                )
            }
            Text(
                text = "O pagamento será feito no dia do atendimento no petshop.",
                style = sentenceTitleTextStyle,
                color = customColorScheme.primary,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Mostrar erro se houver
        viewModel.errorMessage?.let { error ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors(containerColor = customColorScheme.error.copy(alpha = 0.1f))
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = null,
                        tint = customColorScheme.error,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = error,
                        color = customColorScheme.error,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // Informações de pagamento
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = customColorScheme.primary.copy(alpha = 0.1f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = Color.Green,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "O pagamento será feito no dia do agendamento, diretamente no petshop, antes do serviço.",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Detalhes finais
        Text(
            text = "Seu agendamento será confirmado assim que você clicar no botão abaixo:",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botão confirmar agendamento
        Button(
            onClick = { 
                viewModel.clearError() // Limpa erro anterior
                viewModel.createSchedule(token) 
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = customColorScheme.primary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            enabled = !viewModel.isLoading
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Confirmar Agendamento",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = buttonTextStyle
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Confirmar"
                    )
                }
            }
        }
    }
}

@Composable
private fun PixPaymentScreen(viewModel: CreateScheduleViewModel) {
    val formData = viewModel.currentFormData ?: return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Pagamento via PIX",
            style = sentenceTitleTextStyle,
            color = customColorScheme.primary,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Informações do PIX
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = customColorScheme.primary.copy(alpha = 0.1f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Payment,
                    contentDescription = null,
                    tint = customColorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Escaneie o QR Code ou use a chave PIX abaixo:",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Total: R$ ${formData.totalPrice}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = customColorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Placeholder para QR Code (sua colega implementará)
        Card(
            modifier = Modifier
                .size(200.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Gray.copy(alpha = 0.2f))
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "QR Code PIX\n(Em desenvolvimento)",
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Chave PIX placeholder
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = CardDefaults.outlinedCardBorder()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Chave PIX:",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "pix@petcare.com.br",
                    fontSize = 16.sp,
                    fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botão continuar (temporário até implementação do PIX)
        Button(
            onClick = { viewModel.nextStep() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = customColorScheme.primary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Pagamento Concluído",
                style = buttonTextStyle
            )
        }
    }
}

@Composable
private fun CashConfirmationScreen(viewModel: CreateScheduleViewModel) {
    val formData = viewModel.currentFormData ?: return

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header
        Text(
            text = "Agendamento Confirmado!",
            style = sentenceTitleTextStyle,
            color = customColorScheme.primary,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Ícone de sucesso
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            tint = Color.Green,
            modifier = Modifier.size(80.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Informações sobre pagamento presencial
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = customColorScheme.primary.copy(alpha = 0.1f))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Pagamento Presencial",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = customColorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "• O pagamento será feito diretamente no petshop",
                    fontSize = 14.sp
                )
                Text(
                    text = "• Nossa equipe atualizará o sistema após o atendimento",
                    fontSize = 14.sp
                )
                Text(
                    text = "• Você receberá uma notificação quando o status for atualizado",
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Valor: R$ ${formData.totalPrice}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = customColorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Resumo do agendamento
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = CardDefaults.outlinedCardBorder()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Resumo do Agendamento",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("📅 ${formData.date} às ${formData.time}", fontSize = 14.sp)
                Text("🐕 ${formData.pet.name}", fontSize = 14.sp)
                Text("💼 ${formData.services.joinToString { it.name }}", fontSize = 14.sp)
                formData.employee?.let {
                    Text("👨‍⚕️ ${it.name}", fontSize = 14.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botão concluir
        Button(
            onClick = { viewModel.nextStep() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = customColorScheme.primary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Concluir",
                style = buttonTextStyle
            )
        }
    }
}

@Composable
private fun SuccessScreen(viewModel: CreateScheduleViewModel, onDismiss: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        // Ícone de sucesso
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(
                    Color.Green.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(50.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = null,
                tint = Color.Green,
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Agendamento cadastrado com sucesso!",
            style = sentenceTitleTextStyle,
            color = customColorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Seu agendamento foi confirmado. Você receberá um lembrete no dia do atendimento.",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onDismiss,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = customColorScheme.primary,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Concluir",
                style = buttonTextStyle
            )
        }
    }
}