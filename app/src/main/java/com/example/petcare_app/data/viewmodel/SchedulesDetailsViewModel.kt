package com.example.petcare_app.data.viewmodel

import TokenDataStore
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.dto.ScheduleDetailsResponseDTO
import com.example.petcare_app.data.repository.ScheduleRepository
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class SchedulesDetailsViewModel(
    private val scheduleRepository: ScheduleRepository,
) : ViewModel(){

    var scheduleDetailsResponseDTO = mutableStateOf<ScheduleDetailsResponseDTO?>(null)
        private set

    @SuppressLint("NewApi")
    fun getScheduleDetails(token: String, id: Int){
        viewModelScope.launch {
            try {
                scheduleDetailsResponseDTO.value = scheduleRepository.getAllScheduleDetailById(token, id).body();

                val dto = scheduleDetailsResponseDTO.value

                val timeRange = if (!dto?.scheduleDate.isNullOrEmpty() && !dto?.scheduleTime.isNullOrEmpty()) {
                    try {
                        val dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
                        val startDateTime = LocalDateTime.parse(dto!!.scheduleDate, dateTimeFormatter)

                        val durationParts = dto.scheduleTime!!.split(":").map { it.toLong() }
                        val duration = Duration.ofHours(durationParts[0])
                            .plusMinutes(durationParts[1])
                            .plusSeconds(durationParts[2])

                        val endDateTime = startDateTime.plus(duration)
                        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")

                        "${startDateTime.format(timeFormatter)} - ${endDateTime.format(timeFormatter)}"
                    } catch (e: Exception) {
                        "Horário inválido"
                    }
                } else {
                    "Horário indisponível"
                }

                val dateTime = LocalDateTime.parse(scheduleDetailsResponseDTO.value?.scheduleDate, DateTimeFormatter.ISO_LOCAL_DATE_TIME)

                val outputFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale("pt", "BR"))

                val formattedDate = dateTime.format(outputFormatter)

                val payment = when(scheduleDetailsResponseDTO.value?.paymentMethod){
                    "PIX" -> "Pix"
                    "CARTAO_DEBITO" -> "Cartão de Débito"
                    "CARTAO_CREDITO" -> "Cartão de Crédito"
                    else -> "Dinheiro"
                }

                scheduleDetailsResponseDTO.value = dto?.copy(
                    paymentMethod = payment,
                    scheduleTime = timeRange,
                    scheduleDate = formattedDate
                )

            }
            catch (e:Exception){
                Log.e("API_ERROR at ScheduleDetailsViewModel", "Erro: ${e.message}")
            }
        }
    }
}