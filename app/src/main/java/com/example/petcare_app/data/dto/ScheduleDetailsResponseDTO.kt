package com.example.petcare_app.data.dto

import com.example.petcare_app.data.model.Services
import java.time.LocalDateTime
import java.time.LocalTime


data class ScheduleDetailsResponseDTO(
    val id: Int?,
    val scheduleStatus: String?,
    val scheduleDate: String?,
    val scheduleTime: String?,
    val petName: String?,
    val paymentMethod: String?,
    val paymentStatus: String?,
    val scheduleNote: String?,
    val services: List<Services>?
)
