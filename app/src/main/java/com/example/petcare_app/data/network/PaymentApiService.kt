package com.example.petcare_app.data.network

import com.example.petcare_app.data.model.PaymentResponse
import retrofit2.Response
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentApiService {
    @POST("api/payments/pix/{userId}")
    suspend fun createPixPayment(@Path("userId") userId: Int): Response<PaymentResponse>
}