package com.example.petcare_app.data.repository

import com.example.petcare_app.data.dto.ScheduleDTO
import com.example.petcare_app.data.dto.SchedulePUTDTO
import com.example.petcare_app.data.dto.ScheduleCreateDTO
import com.example.petcare_app.data.dto.ScheduleDetailsDTO
import com.example.petcare_app.data.model.Schedule
import com.example.petcare_app.data.services.ScheduleService
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import java.time.LocalDateTime

interface ScheduleRepository {
    val api : ScheduleRepository

    suspend fun getAllSchedulesMonthByUser(
        token: String, 
        id: Int, 
        month: LocalDateTime
    ): Response<List<Schedule>> {
        return api.getAllSchedulesMonthByUser(token, id, month)
    }

    suspend fun getAllSchedulesByUser(
        token: String,
        id: Int
    ) : Response<List<ScheduleDTO>> {
        return api.getAllSchedulesByUser(token, id)
    }

    suspend fun createSchedule(
        token: String, 
        scheduleCreateDTO: ScheduleCreateDTO
    ): Response<Schedule> {
        return api.createSchedule(token, scheduleCreateDTO)
    }
    
    suspend fun getAllSchedulesMonthByPet(
        token: String, 
        id: Int, 
        date: LocalDateTime
    ): Response<List<Schedule>> {
        return api.getAllSchedulesMonthByPet(token, id, date)
    }

    suspend fun reviewScheduleByID(
        token: String,
        id: Int,
        nota: Int
    ) : Response<SchedulePUTDTO> {
        return api.reviewScheduleByID(token, id, nota)
    }

    suspend fun cancelSchedule(
        token: String,
        id: Int,
        status: String
    ) : Response<SchedulePUTDTO> {
        return api.cancelSchedule(token, id, status)
    }

    suspend fun getScheduleByID(
        token: String,
        id: Int
    ) : Response<ScheduleDetailsDTO> {
        return api.getScheduleByID(token, id)
    }
}