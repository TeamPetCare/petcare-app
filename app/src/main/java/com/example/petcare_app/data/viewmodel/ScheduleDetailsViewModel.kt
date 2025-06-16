package com.example.petcare_app.data.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.dto.ScheduleDetailsDTO
import com.example.petcare_app.data.dto.SchedulePUTDTO
import com.example.petcare_app.data.model.Payment
import com.example.petcare_app.data.model.PaymentModel
import com.example.petcare_app.data.model.Schedule
import com.example.petcare_app.data.network.RetrofitInstance
import com.example.petcare_app.data.services.PaymentService
import com.example.petcare_app.data.services.ScheduleService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class ScheduleDetailsViewModel : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    private val _scheduleInfo = MutableStateFlow<ScheduleDetailsDTO?>(null)
    val scheduleInfo: StateFlow<ScheduleDetailsDTO?> = _scheduleInfo

    fun cancelSchedule(token: String, id: Int) {
        val api = RetrofitInstance.retrofit.create(ScheduleService::class.java)

        viewModelScope.launch {
            isLoading = true

            try {
                val response = api.cancelSchedule(
                    token = token,
                    id = id,
                    status = "CANCELADO"
                )

                if (response.isSuccessful) {
                    Log.d("API_SUCCESS", "Agendamento cancelado com sucesso.")
                    getScheduleInfoByID(token, id)
                } else {
                    Log.d("API_ERROR", "Erro body: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Erro de conexão: ${e.message}", e)
            }

            isLoading = false
        }
    }

    fun getScheduleInfoByID(token: String, id: Int) {
        val api = RetrofitInstance.retrofit.create(ScheduleService::class.java)

        viewModelScope.launch {
            isLoading = true

            try {
                val response = api.getScheduleByID(
                    token = token,
                    id = id
                )

                if (response.isSuccessful) {
                    val schedules = response.body()
                    _scheduleInfo.value = schedules
                } else {
                    Log.d("API_ERROR", "Erro body: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Erro de conexão: ${e.message}", e)
            }

            isLoading = false
        }
    }

}