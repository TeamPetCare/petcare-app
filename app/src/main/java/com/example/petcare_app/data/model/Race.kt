package com.example.petcare_app.data.model

import java.time.LocalDateTime

data class Race(
    val id: Int? = null,
    val raceType: String,
    val price: Double,
    val deletedAt: String? = null
)
