package com.example.petcare_app.data.model

import java.time.LocalDateTime
import java.time.LocalTime

data class Schedule(
    val id: Int? = null,
    val scheduleStatus: String,
    val scheduleDate: String,
    val scheduleTime: String,
    val creationDate: String,
    val scheduleNote: String,
    val deletedAt: String? = null,
    val pet: Pet,
    val payment: PaymentModel? = null,
    val services: List<Services> = emptyList(),
    val employee: User? = null
)
