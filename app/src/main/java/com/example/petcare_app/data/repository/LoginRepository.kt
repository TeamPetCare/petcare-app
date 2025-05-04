package com.example.petcare_app.data.repository

import com.example.petcare_app.data.dto.PetCreateDTO
import com.example.petcare_app.data.model.LoginRequest
import com.example.petcare_app.data.model.LoginResponse
import com.example.petcare_app.data.model.Pet
import com.example.petcare_app.data.services.LoginService
import com.example.petcare_app.data.services.PetService
import com.example.petcare_app.datastore.TokenDataStore
import com.example.petcare_app.utils.SessionManager
import okhttp3.ResponseBody
import retrofit2.Response

class LoginRepository(private val api: LoginService) {
    suspend fun login(email: String, password: String): Response<LoginResponse> {
        val request = LoginRequest(email, password)
        return api.login(request)
    }

    suspend fun verifyToken(token: String): Response<ResponseBody> {
        return api.verifyToken(token)
    }
}
