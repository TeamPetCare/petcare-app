package com.example.petcare_app.data.model

import java.time.LocalDateTime

data class Notification(
    val id: Int = 0,
    val notificationType: String,
    val title: String,
    val description: String,
    val notificationDate: LocalDateTime,
    val saw: Boolean,
    val deletedAt: LocalDateTime? = null,
    val userId: Int
)
