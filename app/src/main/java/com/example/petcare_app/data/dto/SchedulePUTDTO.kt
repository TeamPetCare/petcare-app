package com.example.petcare_app.data.dto

data class SchedulePUTDTO(
    val id: Int? = null,
    val scheduleStatus: String,
    val scheduleDate: String,
    val scheduleTime: String,
    val creationDate: String,
    val scheduleNote: String,
    val petId: Int,
    val paymentId: Int,
    val serviceIds: List<Int>? = null,
    val deletedAt: String? = null,
    val employeeId: Int? = null,
    val review: Int? = null
)