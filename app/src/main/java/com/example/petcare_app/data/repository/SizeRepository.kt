package com.example.petcare_app.data.repository

import com.example.petcare_app.data.model.Size
import com.example.petcare_app.data.services.SizeService
import retrofit2.Response

interface SizeRepository {
    val api : SizeService
    suspend fun getSizes(token: String) : Response<List<Size>> {
        return api.getSizes(token)
    }
}