package com.example.petcare_app.ui.components.plansComponents

import androidx.compose.ui.graphics.Color

data class PlanItem(
    val namePets: String,
    val plan: String,
    val status: String,
    val fontColor: Color,
    val backgroundColor: Color,
)