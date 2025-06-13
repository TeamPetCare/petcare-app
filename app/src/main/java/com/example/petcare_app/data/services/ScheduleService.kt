package com.example.petcare_app.data.services

import com.example.petcare_app.data.dto.ScheduleCreateDTO
import com.example.petcare_app.data.dto.UserCreateDTO
import com.example.petcare_app.data.model.Schedule
import com.example.petcare_app.data.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.LocalDateTime

interface ScheduleService {
    @GET("/api/schedules/client/{id}")
    suspend fun getAllSchedulesMonthByUser(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Query("month") month: LocalDateTime
    ): Response<List<Schedule>>

    @GET("/schedules/pet/{id}")
    suspend fun getAllSchedulesMonthByPet(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body date: LocalDateTime
    ): Response<List<Schedule>>

    @POST("/api/schedules")
    suspend fun createSchedule(
        @Header("Authorization") token: String,
        @Body scheduleCreateDTO: ScheduleCreateDTO
    ): Response<Schedule>
}