package com.example.petcare_app.data.repository

import com.example.petcare_app.data.dto.LoginResponseDto
import com.example.petcare_app.data.dto.UserCreateDTO
import com.example.petcare_app.data.dto.UserUpdateDTO
import com.example.petcare_app.data.model.User
import com.example.petcare_app.data.services.UserService
import retrofit2.Response

class UserRepository (
    private val api : UserService
){
    suspend fun getUsers(token: String) : Response<List<User>> {
        return api.getUsers(token)
    }
    suspend fun updateUser(token: String, user: UserUpdateDTO, id: Int) : Response<User>{
        return api.updateUser(token, user, id)
    }
    suspend fun getUserById(token: String, id: Int) : Response<User>{
        return api.getUserById(token, id)
    }
    suspend fun createUser(user: UserCreateDTO): Response<LoginResponseDto> {
        return api.createUser(user)
    }
}