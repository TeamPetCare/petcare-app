package com.example.petcare_app.data.model

import java.time.LocalDateTime

data class PaymentModel(
    val id: Int = 0,
    val price: Double,
    val paymentDate: LocalDateTime,
    val paymentId: String? = null,
    val paymentMethod: String? = null,
    val paymentStatus: String? = null,
    val userId: Int,
    val qrCodeImageBase64: String? = null,
    val paymentLink: String? = null,
    val deletedAt: LocalDateTime? = null
)
