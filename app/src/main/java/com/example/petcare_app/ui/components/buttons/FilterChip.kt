package com.example.petcare_app.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.ui.screens.HomeScreenApp
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.customColorStatusAgendamentoScheme
import com.example.petcare_app.ui.theme.innerInputTextStyle

@Composable
fun FilterChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) customColorStatusAgendamentoScheme.tertiary else Color.Transparent
    val borderColor = if (isSelected) Color.Transparent else customColorStatusAgendamentoScheme.tertiary
    val textColor = if (isSelected) Color.White else customColorStatusAgendamentoScheme.tertiary

    Box(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(50))
            .border(1.dp, borderColor, RoundedCornerShape(50))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 5.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            style = innerInputTextStyle
        )
    }
}

@Preview
@Composable
fun FilterChipPreview() {
    Row (modifier = Modifier.padding(16.dp)) {
        FilterChip(
            text = "Todos os pets",
            isSelected = true,
            onClick = { }
        )
        FilterChip(
            text = "Madonna",
            isSelected = false,
            onClick = { }
        )
    }

}