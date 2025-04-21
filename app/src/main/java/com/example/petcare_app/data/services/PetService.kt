package com.example.petcare_app.data.services

import com.example.petcare_app.data.dto.PetCreateDTO
import com.example.petcare_app.data.model.Pet
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface PetService {
    @POST("pets")
    suspend fun createPet(
        @Header("Authorization") token: String,
        @Body pet: PetCreateDTO
    ): Response<Pet>
}