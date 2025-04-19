package com.example.petcare_app.data.model

import java.time.LocalDateTime

data class Specie(
    val id: Int? = null,
    val name: String,
    val price: Double,
    val deletedAt: LocalDateTime? = null
)
