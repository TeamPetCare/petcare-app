package com.example.petcare_app.ui.components.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.example.petcare_app.ui.theme.montserratFontFamily

// Usado no status do pagamento do agendamento e no status de um plano
@Composable
fun StatusComposable(
    icon: ImageVector,
    status: String,
    fontColor: Color,
    backgroundColor: Color,
    textColor: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(backgroundColor, shape = RoundedCornerShape(50))
            .padding(horizontal = 8.dp, vertical = 2.dp)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(Color.White, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = fontColor,
                modifier = Modifier.size(8.dp)
            )
        }

        androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(horizontal = 2.dp))

        Text(
            text = status,
            color = textColor,
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StatusComposablePreview() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        StatusComposable(
            icon = Icons.Default.Check,
            status = "Ativo",
            fontColor = Color.Green,
            backgroundColor = Color(0xFF4CAF50),
            textColor = Color.White
        )

        StatusComposable(
            icon = Icons.Default.Close,
            status = "Inativo",
            fontColor = Color.Red,
            backgroundColor = Color(0xFFF44336),
            textColor = Color.White
        )
    }
}