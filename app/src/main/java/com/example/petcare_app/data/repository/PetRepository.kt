package com.example.petcare_app.data.repository

import com.example.petcare_app.data.dto.PetCreateDTO
import com.example.petcare_app.data.model.Pet
import com.example.petcare_app.data.services.PetService
import retrofit2.Response

interface PetRepository {
    val api : PetService
    suspend fun createPet(token: String, pet: PetCreateDTO): Response<Pet> {
        return api.createPet(token, pet)
    }
}