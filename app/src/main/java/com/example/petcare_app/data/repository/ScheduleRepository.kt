package com.example.petcare_app.data.repository

import com.example.petcare_app.data.model.Schedule
import retrofit2.Response
import java.time.LocalDateTime

interface ScheduleRepository {
    val api : ScheduleRepository

    suspend fun getAllSchedulesMonthByUser(
        token: String,
        id: Int,
        dateTime: LocalDateTime
    ) : Response<List<Schedule>> {
        return api.getAllSchedulesMonthByUser(token, id, dateTime)
    }

    suspend fun getAllSchedulesMonthByPet(
        token: String,
        id: Int,
        dateTime: LocalDateTime
    ) : Response<List<Schedule>> {
        return api.getAllSchedulesMonthByPet(token, id, dateTime)
    }
}