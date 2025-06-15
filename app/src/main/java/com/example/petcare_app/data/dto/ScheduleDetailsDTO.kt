package com.example.petcare_app.data.dto

import com.example.petcare_app.data.model.PaymentModel
import com.example.petcare_app.data.model.Pet
import com.example.petcare_app.data.model.PlanType
import com.example.petcare_app.data.model.Plans
import com.example.petcare_app.data.model.Services
import com.example.petcare_app.data.model.User

data class ScheduleDetailsDTO (
    val id: Int? = 0,
    val review: Int,
    val scheduleStatus: String,
    val scheduleDate: String,
    val scheduleTime: String,
    val creationDate: String,
    val petName: String,
    val paymentLink: String,
    val paymentStatus: String,
    val paymentMethod: String,
    val scheduleNote: String,
    val services: List<Services>
)