package com.example.petcare_app.data.model

import java.time.LocalDateTime

data class Plans(
    val id: Int = 0,
    val subscriptionDate: LocalDateTime,
    val name: String,
    val price: Double,
    val active: Boolean,
    val renewal: Int,
    val description: String,
    val hasDiscount: Boolean? = null,
    val planTypeId: Int,
    val servicesIds: List<Int> = emptyList(),
    val repeatQuantity: List<Int> = emptyList(),
    val deletedAt: LocalDateTime? = null,
    val paymentsIds: List<Int> = emptyList(),
    val petsIds: List<Int> = emptyList()
)
