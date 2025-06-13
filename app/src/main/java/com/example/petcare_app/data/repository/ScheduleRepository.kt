package com.example.petcare_app.data.repository

import com.example.petcare_app.data.dto.ScheduleDetailsResponseDTO
import com.example.petcare_app.data.model.Schedule
import com.example.petcare_app.data.services.ScheduleService
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Path
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

    suspend fun getAllScheduleDetailById(
        token: String,
        id: Int
    ) : Response<ScheduleDetailsResponseDTO>{
        return api.getScheduleDetailById(token, id)
    }
}