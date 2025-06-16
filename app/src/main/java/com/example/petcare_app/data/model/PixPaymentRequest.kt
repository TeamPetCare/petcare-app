package com.example.petcare_app.data.model

data class PixPaymentRequest(
    val amount: Double,
    val email: String,
    val name: String,
    val cpf: String
)

data class PixPaymentResponse(
    val id: Int?,
    val price: Double?,
    val paymentDate: String?, // LocalDateTime como String na API
    val paymentId: String?,
    val paymentMethod: String?, // Enum como String
    val paymentStatus: String?, // Enum como String
    val user: User?, // Objeto User
    val qrCodeImageBase64: String?,
    val paymentLink: String?,
    val deletedAt: String? // LocalDateTime como String na API
) 