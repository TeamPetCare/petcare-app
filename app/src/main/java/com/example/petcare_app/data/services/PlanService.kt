package com.example.petcare_app.data.services

import com.example.petcare_app.data.dto.PetByUserIdDTO
import com.example.petcare_app.data.dto.PlanInfoDTO
import com.example.petcare_app.data.model.Plans
import com.example.petcare_app.data.model.Services
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PlanService {
    @GET("plans/user/{id}")
    suspend fun getPlansByUserId(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : Response<List<PlanInfoDTO>>

    @GET("plans")
    suspend fun getPlans(@Header("Authorization") token: String): Response<List<Plans>>

    @GET("services")
    suspend fun getServices(@Header("Authorization") token: String): Response<List<Services>>
}