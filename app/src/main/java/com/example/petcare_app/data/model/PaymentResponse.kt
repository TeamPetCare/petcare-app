package com.example.petcare_app.data.model

data class PaymentResponse(
    val id: Int,
    val price: Double,
    val paymentStatus: String,
    val qrCodeImageBase64: String,
    val user: User
)