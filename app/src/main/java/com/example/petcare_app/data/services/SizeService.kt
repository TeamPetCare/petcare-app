package com.example.petcare_app.data.services

import com.example.petcare_app.data.model.Size
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface SizeService {
    @GET("sizes")
    suspend fun getSizes(@Header("Authorization") token: String): Response<List<Size>>
}