package com.example.petcare_app.data.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.model.Race
import com.example.petcare_app.data.model.Schedule
import com.example.petcare_app.data.model.Size
import com.example.petcare_app.data.model.Specie
import com.example.petcare_app.data.network.RetrofitInstance
import com.example.petcare_app.data.services.ScheduleService
import com.example.petcare_app.data.services.SpecieService
import com.example.petcare_app.datastore.TokenDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SchedulesHomeAppViewModel : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    private val _allSchedulesMonth = MutableStateFlow<List<Schedule>>(emptyList())
    val allSchedulesMonth: StateFlow<List<Schedule>> = _allSchedulesMonth

    private val _allPetsUser = MutableStateFlow<List<Pet>>(emptyList())
    val allPetsUser: StateFlow<List<Pet>> = _allPetsUser

    fun getAllSchedulesMonthByUser(token: String, id: Int, dateTime: LocalDateTime) {
        val api = RetrofitInstance.retrofit.create(ScheduleService::class.java)

        viewModelScope.launch {
            isLoading = true

            Log.d("API_CALL", "Iniciando chamada para buscar agendamentos...")
            Log.d("API_CALL", "Token: $token")
            Log.d("API_CALL", "ID: $id")
            Log.d("API_CALL", "Data atual enviada: $dateTime")

            try {
                val response = api.getAllSchedulesMonthByUser(
                    token = token,
                    id = id,
                    month = dateTime
                )

                Log.d("API_RESPONSE", "Status Code: ${response.code()}")
                if (response.isSuccessful) {
                    val schedules = response.body()
                    Log.d("API_RESPONSE", "Schedules recebidos: $schedules")
                    _allSchedulesMonth.value = schedules ?: emptyList()
                } else {
                    Log.d("API_ERROR", "Erro body: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Erro de conexão: ${e.message}", e)
            }

            isLoading = false
            Log.d("API_CALL", "Finalizou chamada de busca de agendamentos")
        }
    }


    fun getAllPetsUser(){}
}