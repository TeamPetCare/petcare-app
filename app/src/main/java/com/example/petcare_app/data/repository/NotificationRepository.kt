package com.example.petcare_app.data.repository

import com.example.petcare_app.data.dto.NotificationResponseDTO
import com.example.petcare_app.data.services.NotificationService
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Path

class NotificationRepository(
    private val notificationService: NotificationService
) {

    suspend fun getNotificationById(
        token: String,
        id: Int
    ) : Response<NotificationResponseDTO> {
        return notificationService.getNotificationById(token, id)
    }

    suspend fun getAllUserNotificationsById(
        token: String,
        id: Int,
        page: Int
    ) : Response<List<NotificationResponseDTO>>{
        return notificationService.getAllUserNotificationsById(token, id, page)
    }


}