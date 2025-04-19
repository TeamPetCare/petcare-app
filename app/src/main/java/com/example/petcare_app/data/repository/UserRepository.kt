package com.example.petcare_app.data.repository

import com.example.petcare_app.data.model.User
import com.example.petcare_app.data.services.UserService
import retrofit2.Response

interface UserRepository {
    val api : UserService
    suspend fun getUsers(token: String) : Response<List<User>> {
        return api.getUsers(token)
    }
    suspend fun getUserById(token: String, id: Int) : Response<User>{
        return api.getUserById(token, id)
    }
}