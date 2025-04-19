package com.example.petcare_app.data.model

import java.math.BigDecimal
import java.time.LocalDateTime

data class Size(
    val id: Int? = null,
    val sizeType: String,
    val price: BigDecimal,
    val deletedAt: LocalDateTime? = null
)
