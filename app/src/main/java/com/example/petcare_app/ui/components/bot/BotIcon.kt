package com.example.petcare_app.ui.components.bot

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.petcare_app.R

@Composable
fun BotIcon(
    onPopupToggle: () -> Unit,
) {
    Box(

    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(MaterialTheme.colorScheme.primary, shape = CircleShape)
                .clickable { onPopupToggle() }
                .align(Alignment.BottomEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_bot),
                contentDescription = "Icon do bot",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Center),
                tint = Color.White
            )
        }
    }
}