package com.example.petcare_app.data.repository

import com.example.petcare_app.data.dto.ScheduleCreateDTO
import com.example.petcare_app.data.model.Schedule
import com.example.petcare_app.data.services.ScheduleService
import retrofit2.Response
import java.time.LocalDateTime

class ScheduleRepository(private val api: ScheduleService) {
    
    suspend fun getAllSchedulesMonthByUser(
        token: String, 
        id: Int, 
        month: LocalDateTime
    ): Response<List<Schedule>> {
        return api.getAllSchedulesMonthByUser(token, id, month)
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
}