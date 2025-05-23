package com.example.petcare_app.data.repository

import com.example.petcare_app.data.dto.PlanInfoDTO
import com.example.petcare_app.data.services.PlanService
import retrofit2.Response

interface PlanRepository {
    val api : PlanService
    suspend fun getPlansByUserId(token: String, id: Int): Response<List<PlanInfoDTO>> {
        return api.getPlansByUserId(token, id)
    }
}