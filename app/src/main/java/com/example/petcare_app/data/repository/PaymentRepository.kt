package com.example.petcare_app.data.repository

import com.example.petcare_app.data.model.PaymentResponse
import com.example.petcare_app.data.network.RetrofitInstance
import retrofit2.Response

class PaymentRepository {

    private val paymentApi = RetrofitInstance.paymentApi

    suspend fun createPixPayment(userId: Int): Response<PaymentResponse> {
        return paymentApi.createPixPayment(userId)
    }
}