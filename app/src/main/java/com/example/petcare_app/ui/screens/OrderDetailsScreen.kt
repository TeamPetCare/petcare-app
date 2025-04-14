package com.example.petcare_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.R

enum class OrderStatus {
    CONCLUIDO,
    AGENDADO,
    CANCELADO
}

data class OrderDetail(
    val id: String,
    val date: String,
    val timeRange: String,
    val status: OrderStatus,
    val address: String,
    val petName: String,
    val paymentMethod: String,
    val isPaid: Boolean,
    val description: String,
    val services: Map<String, Double>
)

@Composable
fun OrderDetailsScreen(
    orderDetail: OrderDetail,
    onBackClick: () -> Unit = {},
    onDownloadReceipt: () -> Unit = {},
    onRateServiceClick: () -> Unit = {}
) {
    // A tela contém apenas o conteúdo sem o header e footer
    // que serão adicionados manualmente onde você usar este componente
    OrderContent(
        orderDetail = orderDetail,
        onBackClick = onBackClick,
        onRateServiceClick = onRateServiceClick,
        onDownloadReceipt = onDownloadReceipt
    )
}

@Composable
fun OrderContent(
    orderDetail: OrderDetail,
    onBackClick: () -> Unit,
    onRateServiceClick: () -> Unit,
    onDownloadReceipt: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        // Barra superior com botão voltar e título
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Voltar",
                    tint = Color(0xFF006778)
                )
            }

            Text(
                text = "Banho e Tosa #${orderDetail.id}",
                color = Color(0xFF006778),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            // Badge de status
            StatusBadge(status = orderDetail.status)
        }

        // Data e hora
        Text(
            text = "${orderDetail.date} | ${orderDetail.timeRange}",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        // Botão de avaliação
        RateServiceButton(
            enabled = orderDetail.status == OrderStatus.CONCLUIDO,
            onClick = onRateServiceClick
        )

        // Seção de endereço
        SectionTitle(title = "Endereço")
        Text(
            text = orderDetail.address,
            fontSize = 14.sp,
            color = Color(0xFF006778),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Informações do animal e pagamento
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        ) {
            Column(modifier = Modifier.weight(1f)) {
                SectionTitle(title = "Pet")
                Text(
                    text = orderDetail.petName,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                SectionTitle(title = "Pagamento")
                Text(
                    text = orderDetail.paymentMethod,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

            // Badge de pagamento
            PaymentBadge(isPaid = orderDetail.isPaid)
        }

        // Descrição
        SectionTitle(title = "Descrição")
        Text(
            text = orderDetail.description,
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Serviços e preços
        SectionTitle(title = "Serviços")
        orderDetail.services.forEach { (service, price) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = service,
                    fontSize = 16.sp,
                    color = Color(0xFF006778),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "R$${String.format("%.2f", price)}",
                    fontSize = 16.sp,
                    color = Color(0xFF006778),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        // Total
        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = Color.LightGray
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF006778)
            )

            Text(
                text = "R$${String.format("%.2f", orderDetail.services.values.sum())}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF006778)
            )
        }

        // Botão de baixar comprovante
        DownloadReceiptButton(onClick = onDownloadReceipt)
    }
}

@Composable
fun DownloadReceiptButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF006778)
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Baixar comprovante",
                color = Color.White
            )
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Baixar",
                tint = Color.White
            )
        }
    }
}

@Composable
fun StatusBadge(status: OrderStatus) {
    val (backgroundColor, textColor) = when (status) {
        OrderStatus.CONCLUIDO -> Color(0xFFCCFFCC) to Color(0xFF006400)
        OrderStatus.AGENDADO -> Color(0xFFCCE5FF) to Color(0xFF0055A4)
        OrderStatus.CANCELADO -> Color(0xFFFFCCCC) to Color(0xFF990000)
    }

    val text = when (status) {
        OrderStatus.CONCLUIDO -> "Concluído"
        OrderStatus.AGENDADO -> "Agendado"
        OrderStatus.CANCELADO -> "Cancelado"
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun PaymentBadge(isPaid: Boolean) {
    val (backgroundColor, textColor, text) = if (isPaid) {
        Triple(Color(0xFFCCFFCC), Color(0xFF006400), "Pago")
    } else {
        Triple(Color(0xFFFFCCCC), Color(0xFF990000), "Não Pago")
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun RateServiceButton(
    enabled: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (enabled) Color(0xFF006778) else Color.LightGray
    val textColor = if (enabled) Color.White else Color.Gray

    Button(
        onClick = { if (enabled) onClick() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.Gray
        ),
        shape = RoundedCornerShape(20.dp),
        enabled = enabled
    ) {
        Text(
            text = "Avaliar Atendimento",
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

@Composable
fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        fontSize = 12.sp,
        color = Color.Gray,
        modifier = modifier
    )
}

// Função de Preview limpa apenas para o conteúdo central
@Preview(showBackground = true)
@Composable
fun OrderDetailsScreenPreview() {
    val sampleOrder = OrderDetail(
        id = "001",
        date = "17 Jan 2025",
        timeRange = "10:00-11:00",
        status = OrderStatus.AGENDADO,  // Status Agendado como na imagem
        address = "Avenida Inocêncio Seráfico",
        petName = "Rex",
        paymentMethod = "Cartão de Crédito",
        isPaid = true,
        description = "Ainda não há detalhes sobre este atendimento.",
        services = mapOf(
            "Banho" to 20.00,
            "Tosa" to 40.00
        )
    )

    // Apenas o conteúdo central
    OrderDetailsScreen(orderDetail = sampleOrder)
}