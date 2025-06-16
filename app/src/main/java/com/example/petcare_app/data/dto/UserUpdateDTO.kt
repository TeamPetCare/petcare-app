package com.example.petcare_app.data.dto

data class UserUpdateDTO(
    val name: String,
    val userImg: String? = null,
    val email: String,
    val password: String? = null,
    val cellphone: String,
    val role: String, // ou Role, se for enum no Android também
    val street: String,
    val number: Int,
    val complement: String? = null,
    val cep: String,
    val district: String,
    val city: String,
    val cnpjOwner: String? = null,
    val roleEmployee: String? = null,
    val disponibilityStatus: Boolean? = null,
    val cpfClient: String? = null,
    val petIds: List<Int>? = null
) {
}