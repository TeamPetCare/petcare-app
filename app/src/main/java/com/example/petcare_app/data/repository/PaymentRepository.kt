package com.example.petcare_app.data.repository

import com.example.petcare_app.data.model.Payment
import com.example.petcare_app.data.services.PaymentService
import retrofit2.Response

interface PaymentRepository {
    val api : PaymentService

    suspend fun getPaymentByID(
        token: String,
        id: Int
    ) : Response<Payment> {
        return api.getPaymentsByID(token, id)
    }

}