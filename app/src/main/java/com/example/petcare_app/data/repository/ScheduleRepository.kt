package com.example.petcare_app.data.repository

import com.example.petcare_app.data.dto.ScheduleDTO
import com.example.petcare_app.data.dto.SchedulePUTDTO
import com.example.petcare_app.data.model.Schedule
import retrofit2.Response
import java.time.LocalDateTime

interface ScheduleRepository {
    val api : ScheduleRepository

    suspend fun getAllSchedulesByUser(
        token: String,
        id: Int
    ) : Response<List<ScheduleDTO>> {
        return api.getAllSchedulesByUser(token, id)
    }

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

    suspend fun reviewScheduleByID(
        token: String,
        id: Int,
        nota: Int
    ) : Response<SchedulePUTDTO> {
        return api.reviewScheduleByID(token, id, nota)
    }
}