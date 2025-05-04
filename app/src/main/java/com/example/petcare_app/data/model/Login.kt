package com.example.petcare_app.data.model

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val name: String,
    val token: String,
    val id: Int
)