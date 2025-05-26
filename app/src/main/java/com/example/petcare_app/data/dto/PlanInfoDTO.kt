package com.example.petcare_app.data.dto

data class PlanInfoDTO (
    val id: Int,
    val active: Boolean,
    val planTypeName: String,
    val petNames: List<String>
)