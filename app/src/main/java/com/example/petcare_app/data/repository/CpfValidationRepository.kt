package com.example.petcare_app.data.repository

import com.example.petcare_app.data.services.CpfValidationService
import retrofit2.Response

interface CpfValidationRepository {
    val api : CpfValidationService
    suspend fun validateCpf(
        cpf: String
    ) : Response<Boolean> {
        return api.validateCpf(cpf)
    }
}