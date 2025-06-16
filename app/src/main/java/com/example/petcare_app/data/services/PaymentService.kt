package com.example.petcare_app.data.services

import com.example.petcare_app.data.dto.PixPaymentDTO
import com.example.petcare_app.data.dto.SchedulePUTDTO
import com.example.petcare_app.data.model.Payment
import com.example.petcare_app.data.model.PaymentModel
import com.example.petcare_app.data.model.PixPaymentRequest
import com.example.petcare_app.data.model.PixPaymentResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

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

    @POST("/api/payments/pix/13")
    suspend fun createPixPayment(
        @Header("Authorization") token: String,
        @Body pixPaymentRequest: PixPaymentRequest
    ): Response<PixPaymentResponse>
}