package com.example.petcare_app.data.model

import java.time.LocalDateTime

data class PlanType(
    val id: Int = 0,
    val name: String,
    val disponibility: Boolean,
    val price: Double,
    val description: String,
    val paymentInterval: Int,
    val deletedAt: LocalDateTime? = null,
    val servicesIds: List<Int> = emptyList()
)
