package com.example.petcare_app.data.services

import com.example.petcare_app.data.model.Race
import com.example.petcare_app.data.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface RaceService {
    @GET("races")
    suspend fun getRaces(@Header("Authorization") token: String): Response<List<Race>>
}