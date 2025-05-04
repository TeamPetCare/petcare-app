package com.example.petcare_app.data.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CpfValidationService {
    @GET("users/cpf")
    suspend fun validateCpf(@Query("cpf") cpf: String) : Response<Boolean>
}