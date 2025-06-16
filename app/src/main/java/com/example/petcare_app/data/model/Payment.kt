package com.example.petcare_app.data.model

import java.time.LocalDateTime

data class Payment(
    val id: Int,
    val price: Double,
    val paymentDate: String,
    val paymentId: String?,
    val paymentMethod: String?,
    val paymentStatus: String?,
    val deletedAt: String?,
    val user: User?,
    val qrCodeImageBase64: String?,
    val paymentLink: String?
)
