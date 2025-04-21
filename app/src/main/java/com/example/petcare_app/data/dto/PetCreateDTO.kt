package com.example.petcare_app.data.dto

import java.time.LocalDate

data class PetCreateDTO(
    val name: String,
    val gender: String,
    val color: String,
    val estimatedWeight: Double,
    val birthdate: String,
    val petObservations: String,
    val petImg: String? = null,
    val planId: Int? = null,
    val specieId: Int,
    val raceId: Int,
    val sizeId: Int,
    val userId: Int
)
