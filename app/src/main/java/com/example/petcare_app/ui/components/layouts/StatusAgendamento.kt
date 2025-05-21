package com.example.petcare_app.ui.components.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.ui.theme.customColorStatusAgendamentoScheme
import com.example.petcare_app.ui.theme.montserratFontFamily

@Composable
fun StatusAgendamento(
    status: String
) {
    val backgroundColor =
        if (status == "AGENDADO") customColorStatusAgendamentoScheme.onPrimary
        else if (status == "CONCLUIDO") customColorStatusAgendamentoScheme.onSecondary
        else Color(0xFFE0E0E0)

    val circleColor =
        if (status == "AGENDADO") customColorStatusAgendamentoScheme.primary
        else if (status == "CONCLUIDO") customColorStatusAgendamentoScheme.secondary
        else Color(0xFF6D7C84)

    val statusName =
        if (status == "AGENDADO") "Agendado"
        else if (status == "CONCLUIDO") "Concluído"
        else "Cancelado"

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .border(0.5.dp, Color.Transparent, shape = RoundedCornerShape(50))
            .background(backgroundColor, shape = RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(circleColor, shape = CircleShape)
        )

        androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(horizontal = 2.dp))

        Text(
            text = statusName,
            color = Color.Black,
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatusAgendamentoPreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatusAgendamento(
            status = "CONCLUIDO"
        )
        StatusAgendamento(
            status = "CANCELADO"
        )
        StatusAgendamento(
            status = "AGENDADO"
        )
    }
}