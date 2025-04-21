package com.example.petcare_app.data.services

import com.example.petcare_app.data.model.Specie
import com.example.petcare_app.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface SpecieService {
    @GET("species")
    suspend fun getSpecies(@Header("Authorization") token: String): Response<List<Specie>>
}