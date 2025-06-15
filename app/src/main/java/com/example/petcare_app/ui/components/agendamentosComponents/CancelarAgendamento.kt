package com.example.petcare_app.ui.components.agendamentosComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FreeCancellation
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.petcare_app.ui.theme.buttonTextStyle
import com.example.petcare_app.ui.theme.customColorScheme

@Composable
fun CancelarAgendamento(
    cancelScheduleFunction: () -> Unit
) {
    Button(
        onClick = {
            cancelScheduleFunction()
        },
        colors = buttonColors(
            containerColor = customColorScheme.primary,
            contentColor = Color.White,
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.FreeCancellation,
                tint = Color.White,
                contentDescription = "Icone Calendário"
            )
            Text(
                text = "Cancelar Agendamento",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = buttonTextStyle
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CancelarAgendamento() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CancelarAgendamento(
            {  }
        )
    }
}