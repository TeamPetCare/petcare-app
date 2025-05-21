package com.example.petcare_app.data.dto

import com.example.petcare_app.data.model.Plans
import com.example.petcare_app.data.model.Race
import com.example.petcare_app.data.model.Size
import com.example.petcare_app.data.model.Specie

data class PetByUserIdDTO(
    val id: Int,
    val name: String,
    val gender: String,
    val color: String,
    val estimatedWeight: Double,
    val birthdate: String,
    val petObservations: String,
    val petImg: String,
    val deletedAt: String? = null,
    val plan: Plans? = null,
    val specie: Specie,
    val race: Race,
    val size: Size,
    val user: UserFromPetByUserIdDTO
)
