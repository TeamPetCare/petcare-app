package com.example.petcare_app.data.dto

import java.time.LocalDateTime


data class NotificationResponseDTO(
    val id: Int?,
    val notificationType: String?,
    val title: String?,
    val description: String?,
    val createdAt: String?,
    val saw: Boolean?,
    val userId: Int?
)