package com.example.petcare_app.data.services

import com.example.petcare_app.data.dto.PixPaymentDTO
import com.example.petcare_app.data.dto.SchedulePUTDTO
import com.example.petcare_app.data.model.Payment
import com.example.petcare_app.data.model.PaymentModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentService {
    @GET("/api/payments/{id}")
    suspend fun getPaymentsByID(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : Response<Payment>

    @POST("/api/payments/pix/{id}")
    suspend fun createPixPayment(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body pixPaymentData: PixPaymentDTO
    ) : Response<Payment>
}