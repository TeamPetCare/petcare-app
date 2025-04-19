package com.example.petcare_app.data.model

import java.time.LocalDateTime

data class Payment(
    val id: Int = 0,
    val price: Double,
    val paymentDate: LocalDateTime,
    val paymentId: String? = null,
    val paymentMethod: Payment? = null,
    val paymentStatus: Boolean? = null,
    val deletedAt: LocalDateTime? = null,
    val userId: Int
)
