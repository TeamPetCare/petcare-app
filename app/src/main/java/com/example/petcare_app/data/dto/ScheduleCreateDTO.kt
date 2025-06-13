package com.example.petcare_app.data.dto

data class ScheduleCreateDTO (
    val scheduleStatus: String, // Ex: "AGENDADO"
    val scheduleDate: String,   // Formato ISO 8601 - Ex: "2025-05-25T14:30:00"
    val scheduleTime: String,   // Ex: "14:30:00"
    val creationDate: String,   // Ex: "2025-05-25T13:00:00"
    val scheduleNote: String? = null,
    val petId: Int,
    val paymentId: Int? = null,
    val serviceIds: List<Int>,
    val employeeId: Int? = null,
    val deletedAt: String? = null
)
