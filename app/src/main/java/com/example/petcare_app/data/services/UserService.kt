package com.example.petcare_app.data.services

import com.example.petcare_app.data.dto.UserCreateDTO
import com.example.petcare_app.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @GET("users/")
    suspend fun getUsers(@Header("Authorization") token: String): Response<List<User>>

    @GET("users/{id}")
    suspend fun getUserById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<User>

    @POST("users")
    suspend fun createUser(
        @Header("Authorization") token: String,
        @Body user: UserCreateDTO
    ): Response<User>
}