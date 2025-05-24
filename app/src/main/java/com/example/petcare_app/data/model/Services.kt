package com.example.petcare_app.data.model

import java.sql.Time
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.time.Duration

data class Services(
    val id: Int? = null,
    val name: String,
    val note: String,
    val price: Double,
    val estimatedTime: String,
    val disponibility: Boolean,
    val deletedAt: String? = null,
    val isExclusive: Boolean? = null,
    val planTypes: List<PlanType> = emptyList(),
    val plans: List<Plans> = emptyList()
)
