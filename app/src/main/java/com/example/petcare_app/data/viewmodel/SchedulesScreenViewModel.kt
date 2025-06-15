package com.example.petcare_app.data.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.dto.ScheduleDTO
import com.example.petcare_app.data.dto.SchedulePUTDTO
import com.example.petcare_app.data.model.PaymentModel
import com.example.petcare_app.data.model.Schedule
import com.example.petcare_app.data.network.RetrofitInstance
import com.example.petcare_app.data.services.PaymentService
import com.example.petcare_app.data.services.PetService
import com.example.petcare_app.data.services.ScheduleService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SchedulesScreenViewModel : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    private val _allSchedulesByUser = MutableStateFlow<List<ScheduleDTO>>(emptyList())
    val allSchedulesByUser: StateFlow<List<ScheduleDTO>> = _allSchedulesByUser

    private val _allPetsUser = MutableStateFlow<List<PetResumo>>(emptyList())
    val allPetsUser: StateFlow<List<PetResumo>> = _allPetsUser

    private val _scheduleItem = MutableStateFlow<SchedulePUTDTO?>(null)
    val scheduleItem: StateFlow<SchedulePUTDTO?> = _scheduleItem

    @SuppressLint("NewApi")
    fun getAllSchedulesByUser(token: String, id: Int) {
        val api = RetrofitInstance.retrofit.create(ScheduleService::class.java)

        viewModelScope.launch {
            isLoading = true

            try {
                val response = api.getAllSchedulesByUser(
                    token = token,
                    id = id
                )

                if (response.isSuccessful) {
                    val schedules = response.body()
                    val filteredSchedules = schedules
                        ?.filter { it.deletedAt == null }
                        ?.sortedByDescending { LocalDateTime.parse(it.scheduleDate) }
                        ?: emptyList()
                    _allSchedulesByUser.value = filteredSchedules
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
        val api = RetrofitInstance.retrofit.create(PetService::class.java)

        viewModelScope.launch {
            isLoading = true

            try {
                val response = api.getPetByUserId(token, idUser)

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

    fun reviewScheduleByID(token: String, idAgendamento: Int, nota: Int, id: Int) {
        val api = RetrofitInstance.retrofit.create(ScheduleService::class.java)

        viewModelScope.launch {
            isLoading = true

            try {
                val response = api.reviewScheduleByID(token, idAgendamento, nota)

                if (response.isSuccessful) {
                    val novoScheduleItem = response.body()
                    _scheduleItem.value = novoScheduleItem

                    getAllSchedulesByUser(token, id)
                } else {
                    Log.d("API_ERROR", "Erro body: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("API_EXCEPTION_UPDATE_SCHEDULE", "Erro de conexão: ${e.message}", e)
            }

            isLoading = false
        }
    }
}