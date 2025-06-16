package com.example.petcare_app.data.repository

import com.example.petcare_app.data.model.LoginRequest
import com.example.petcare_app.data.model.LoginResponse
import com.example.petcare_app.data.services.ImageService
import com.example.petcare_app.data.services.LoginService
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response

class ImageRepository(private val api: ImageService) {
    suspend fun uploadImage(imageMultiPart: MultipartBody.Part, userId: Int, isUser: Boolean): Response<Unit> {
        return api.uploadUserImage(imageMultiPart, userId, isUser);
    }
}