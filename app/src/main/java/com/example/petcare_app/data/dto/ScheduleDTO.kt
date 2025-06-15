package com.example.petcare_app.data.dto

data class ScheduleDTO (
    val id: Int,
    val petId: Int,
    val petName: String,
    val userId: Int,
    val scheduleDate: String,
    val scheduleTime: String,
    val paymentStatus: String,
    val scheduleStatus: String,
    val serviceNames: List<String>,
    val review: Int? = null,
    val deletedAt: String? = null
)

