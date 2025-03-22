package com.example.petcare_app.ui.components.notificationComponents

import androidx.compose.ui.graphics.Color

data class NotificationItem(
    val title: String,
    val description: String,
    val fontColor: Color,
    val backgroundColor: Color,
    val iconType: String
)