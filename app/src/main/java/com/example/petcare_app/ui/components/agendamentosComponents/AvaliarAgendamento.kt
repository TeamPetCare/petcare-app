package com.example.petcare_app.ui.components.agendamentosComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.ui.theme.montserratFontFamily

@Composable
fun AvaliarAgendamento(
    jaAvaliado: Boolean,
    avaliacao: Int = 0
) {
    val gradient = Brush.horizontalGradient(
        colors = listOf(Color(0xFF1D82A2), Color(0xFF005472))
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(brush = gradient, shape = RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 2.dp)
            .clickable {  }
    ) {
        if (jaAvaliado) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(avaliacao) {
                    Icon(
                        imageVector = Icons.Outlined.StarBorder,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        } else {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.StarBorder,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(14.dp)
            )

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(horizontal = 2.dp))

            Text(
                text = "Avaliar",
                color = Color.White,
                fontFamily = montserratFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp
            )
        }
            }

    }
}

@Preview(showBackground = true)
@Composable
fun StatusComposablePreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AvaliarAgendamento(
            jaAvaliado = true,
            avaliacao = 4
        )

        AvaliarAgendamento(
            jaAvaliado = false,
            avaliacao = 4
        )

        AvaliarAgendamento(
            jaAvaliado = false
        )
    }
}