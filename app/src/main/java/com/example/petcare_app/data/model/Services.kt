package com.example.petcare_app.data.model

import java.sql.Time
import java.time.LocalDateTime

data class Services(
    val id: Int? = null,
    val name: String,
    val note: String,
    val price: Double,
    val estimatedTime: Time,
    val disponibility: Boolean,
    val deletedAt: LocalDateTime? = null,
    val isExclusive: Boolean? = null,
    val planTypes: List<PlanType> = emptyList(),
    val plans: List<Plans> = emptyList()
)
