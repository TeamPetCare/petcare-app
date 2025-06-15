package com.example.petcare_app.data.services

import com.example.petcare_app.data.dto.SchedulePUTDTO
import com.example.petcare_app.data.model.Payment
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface PaymentService {
    @GET("/api/payments/{id}")
    suspend fun getPaymentsByID(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : Response<Payment>
}