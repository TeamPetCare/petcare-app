package com.example.petcare_app.data.services

import com.example.petcare_app.data.dto.PetCreateDTO
import com.example.petcare_app.data.model.LoginRequest
import com.example.petcare_app.data.model.LoginResponse
import com.example.petcare_app.data.model.Pet
import com.example.petcare_app.data.model.User
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {
    @POST("auth/login/customer")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("validate-token")
    suspend fun verifyToken(@Query("token") token: String): Response<ResponseBody>
}