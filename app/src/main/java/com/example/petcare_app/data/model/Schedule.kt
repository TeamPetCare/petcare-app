package com.example.petcare_app.data.model

import java.time.LocalDateTime
import java.time.LocalTime

data class Schedule(
    val id: Int? = null,
    val scheduleStatus: String,
    val scheduleDate: LocalDateTime,
    val scheduleTime: LocalTime,
    val creationDate: LocalDateTime,
    val scheduleNote: String,
    val deletedAt: LocalDateTime? = null,
    val pet: Pet,
    val payment: PaymentModel? = null,
    val services: List<Services> = emptyList(),
    val employee: User? = null
)
