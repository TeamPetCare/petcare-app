package com.example.petcare_app.data.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.model.Schedule
import com.example.petcare_app.data.repository.PetRepository
import com.example.petcare_app.data.repository.ScheduleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SchedulesHomeAppViewModel(
    private val scheduleRepository: ScheduleRepository,
    private val petRepository: PetRepository
) : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    private val _allSchedulesMonth = MutableStateFlow<List<Schedule>>(emptyList())
    val allSchedulesMonth: StateFlow<List<Schedule>> = _allSchedulesMonth

    private val _allPetsUser = MutableStateFlow<List<PetResumo>>(emptyList())
    val allPetsUser: StateFlow<List<PetResumo>> = _allPetsUser

    fun getAllSchedulesMonthByUser(token: String, id: Int, dateTime: LocalDateTime) {
        viewModelScope.launch {
            isLoading = true

            Log.d("API_CALL", "Iniciando chamada para buscar agendamentos...")
            Log.d("API_CALL", "Token: $token")
            Log.d("API_CALL", "ID: $id")
            Log.d("API_CALL", "Data atual enviada: $dateTime")

            try {
                val response = scheduleRepository.getAllSchedulesMonthByUser(
                    token = token,
                    id = id,
                    dateTime = dateTime
                )

                Log.d("API_RESPONSE", "Status Code: ${response.code()}")
                if (response.isSuccessful) {
                    val schedules = response.body()
                    val filteredSchedules = schedules?.filter { it.deletedAt == null } ?: emptyList()
                    _allSchedulesMonth.value = filteredSchedules
                } else {
                    Log.d("API_ERROR", "Erro body: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION", "Erro de conexão: ${e.message}", e)
            }

            isLoading = false
        }
    }

    fun getAllPetsByUserId(token: String, idUser: Int){
        viewModelScope.launch {
            isLoading = true

            try {
                val response = petRepository.getPetByUserId(token, idUser)

                if (response.isSuccessful) {
                    val pets = response.body()

                    val petsFiltrados = pets?.map { pet ->
                        PetResumo(id = pet.id, pet.name)
                    }

                    _allPetsUser.value = petsFiltrados ?: emptyList()
                } else {
                    Log.d("API_ERROR", "Erro body: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION_PETS", "Erro de conexão: ${e.message}", e)
            }

            isLoading = false
        }
    }

}

data class PetResumo (
    val id: Int,
    val name: String
)