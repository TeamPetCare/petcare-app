package com.example.petcare_app.data.model

import java.time.LocalDateTime

data class Payment(
    val id: Int = 0,
    val price: Double,
    val paymentDate: String,
    val paymentId: String? = null,
    val paymentMethod: String? = null,
    val paymentStatus: Boolean? = null,
    val deletedAt: String? = null,
    val userId: Int?
)
