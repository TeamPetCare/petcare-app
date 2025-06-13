package com.example.petcare_app.data.services

import com.example.petcare_app.data.dto.NotificationResponseDTO
import com.example.petcare_app.data.dto.ScheduleDetailsResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface NotificationService {

    @GET("notifications/{id}")
    suspend fun getNotificationById(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ) : Response<NotificationResponseDTO>

    @GET("notifications/user/{id}?size=7")
    suspend fun getAllUserNotificationsById(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Query("page") page: Int
    ) : Response<List<NotificationResponseDTO>>
}