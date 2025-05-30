package com.example.petcare_app.data.repository

import com.example.petcare_app.data.model.Schedule
import com.example.petcare_app.data.services.ScheduleService
import retrofit2.Response
import java.time.LocalDateTime

class ScheduleRepository(
    private val api: ScheduleService
) {
    suspend fun getAllSchedulesMonthByUser(
        token: String,
        id: Int,
        dateTime: LocalDateTime
    ): Response<List<Schedule>> {
        return api.getAllSchedulesMonthByUser(token, id, dateTime)
    }

    suspend fun getAllSchedulesMonthByPet(
        token: String,
        id: Int,
        dateTime: LocalDateTime
    ): Response<List<Schedule>> {
        return api.getAllSchedulesMonthByPet(token, id, dateTime)
    }
}