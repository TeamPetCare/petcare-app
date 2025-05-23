package com.example.petcare_app.data.dto

import com.example.petcare_app.data.model.Payment
import com.example.petcare_app.data.model.Pet
import java.time.LocalDateTime

data class UserFromPetByUserIdDTO(
    val id : Int,
    val name : String,
    val userImg : String,
    val email : String,
    val password : String,
    val cellphone : String,
    val role : String,
    val street : String,
    val number : Int,
    val complement : String,
    val cep : String,
    val district : String,
    val city : String,
    val cnpjOwner : String? = null,
    val roleEmployee : String? = null,
    val disponibilityStatusEmployee : Boolean,
    val cpfClient : String,
    val deletedAt : String? = null,
    val payments : List<Payment>
)
