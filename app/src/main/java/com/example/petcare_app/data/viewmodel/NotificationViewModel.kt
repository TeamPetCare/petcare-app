package com.example.petcare_app.data.viewmodel

import TokenDataStore
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.dto.NotificationResponseDTO
import com.example.petcare_app.data.repository.NotificationRepository
import com.example.petcare_app.ui.components.notificationComponents.NotificationItem
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

class NotificationViewModel(
    val notificationRepository: NotificationRepository
) : ViewModel() {

    var page = mutableStateOf<Int>(0)

    var notificationResponseDTOList: MutableList<NotificationResponseDTO> = mutableListOf()
        private set

    // Trocar para mutableStateListOf para que Compose observe atualizações
    var notificationItemList = mutableStateListOf<NotificationItem>()
        private set

    fun getAllUserNotificationsById(token: String, id: Int) {
        viewModelScope.launch {
            try {
                notificationResponseDTOList = notificationRepository
                    .getAllUserNotificationsById(token, id, page.value)
                    .body()
                    ?.toMutableList() ?: mutableListOf()

                // Atualiza a lista observável com o resultado do map
                val mappedList = notificationResponseDTOList.map { dto -> mapToNotificationItem(dto) }

                // Limpa e adiciona os novos itens para notificar o Compose
                notificationItemList.clear()
                notificationItemList.addAll(mappedList)

            } catch (e: Exception) {
                Log.e("API_ERROR at NotificationViewModel", "Erro: ${e.message}")
            }
        }
    }

    fun deleteNotificationById(token: String, id: Int, userId: Int){
        viewModelScope.launch {
            try {
                val response = notificationRepository.deleteNotificationById(token, id)
                if(response.isSuccessful){
                    getAllUserNotificationsById(token, userId)
                }
            }catch (e: Exception){
                Log.e("API_ERROR at NotificationViewModel", "Erro: ${e.message}")
            }
        }
    }
}

fun mapToNotificationItem(dto: NotificationResponseDTO): NotificationItem {

    val backgroundColor: Color = when (dto.notificationType) {
        "CONFIRMED" -> Color(0, 131, 55)
        "CANCELLED" -> Color(206, 0, 0)
        "UPCOMING" ->  Color(0, 84, 114)
        "PAYMENT" -> Color(255, 210, 105)
        else -> Color.Gray
    }

    val icon: String = when (dto.notificationType) {
        "CONFIRMED" -> "check"
        "CANCELLED" -> "error"
        "UPCOMING" -> "bell"
        else -> "pix"
    }


    var fontColor = Color.White

    if(backgroundColor == Color(255, 210, 105)){
        fontColor = Color(0, 84, 114)
    }

    return NotificationItem(
        dto.id!!,
        dto.title!!,
        dto.description!!,
        fontColor,
        backgroundColor,
        icon
    )
}
