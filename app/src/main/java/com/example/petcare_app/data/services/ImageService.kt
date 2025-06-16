package com.example.petcare_app.data.services

import com.example.petcare_app.data.model.LoginRequest
import com.example.petcare_app.data.model.LoginResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImageService {
    @Multipart
    @POST("images/upload")
    suspend fun uploadUserImage(
        @Part file: MultipartBody.Part,
        @Part("userId") userId: Int,
        @Part("isUser") isUser: Boolean
    ): Response<Unit>
}