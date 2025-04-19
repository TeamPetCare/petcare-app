package com.example.petcare_app.data.model

import java.time.LocalDate
import java.time.LocalDateTime

data class Pet(
    val id: Int = 0,
    val name: String,
    val gender: String,
    val color: String,
    val estimatedWeight: Double,
    val birthdate: LocalDate? = null,
    val petObservations: String,
    val petImg: String? = null,
    val deletedAt: LocalDateTime? = null,
    val planId: Int?,
    val specieId: Int,
    val raceId: Int,
    val sizeId: Int,
    val userId: Int
)
