package com.example.petcare_app.data.model

import java.time.LocalDateTime

data class User(
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
    val cnpjOwner : String,
    val roleEmployee : String,
    val disponibilityStatusEmployee : Boolean,
    val cpfClient : String,
    val deletedAt : LocalDateTime,
    val pets : List<Pet>,
    val payments : List<Payment>
) {

}