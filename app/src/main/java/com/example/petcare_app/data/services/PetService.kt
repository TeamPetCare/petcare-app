package com.example.petcare_app.data.services

import com.example.petcare_app.data.dto.PetByUserIdDTO
import com.example.petcare_app.data.dto.PetCreateDTO
import com.example.petcare_app.data.model.Pet
import com.example.petcare_app.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PetService {
    @POST("pets")
    suspend fun createPet(
        @Body pet: PetCreateDTO
    ): Response<Pet>

    @GET("pets/user/{id}")
    suspend fun getPetByUserId(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : Response<List<PetByUserIdDTO>>

    @GET("pets/user/{id}")
    suspend fun getPetsByUserId(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : Response<List<PetByUserIdDTO>>
}