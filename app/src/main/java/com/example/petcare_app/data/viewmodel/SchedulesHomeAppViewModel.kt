package com.example.petcare_app.data.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.dto.PetByUserIdDTO
import com.example.petcare_app.data.dto.SchedulePUTDTO
import com.example.petcare_app.data.model.Race
import com.example.petcare_app.data.model.Schedule
import com.example.petcare_app.data.model.Size
import com.example.petcare_app.data.model.Specie
import com.example.petcare_app.data.network.RetrofitInstance
import com.example.petcare_app.data.repository.PetRepository
import com.example.petcare_app.data.repository.ScheduleRepository
import com.example.petcare_app.data.services.PetService
import com.example.petcare_app.data.services.ScheduleService
import com.example.petcare_app.data.services.SpecieService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class SchedulesHomeAppViewModel(
    private val scheduleRepository: ScheduleRepository,
    private val petRepository: PetRepository,
) : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    private val _allSchedulesMonth = MutableStateFlow<List<Schedule>>(emptyList())
    val allSchedulesMonth: StateFlow<List<Schedule>> = _allSchedulesMonth

    private val _allPetsUser = MutableStateFlow<List<PetResumo>>(emptyList())
    val allPetsUser: StateFlow<List<PetResumo>> = _allPetsUser

    private val _scheduleItem = MutableStateFlow<SchedulePUTDTO?>(null)
    val scheduleItem: StateFlow<SchedulePUTDTO?> = _scheduleItem

    @SuppressLint("NewApi")
    fun getAllSchedulesMonthByUser(token: String, id: Int, dateTime: LocalDateTime) {
        viewModelScope.launch {
            isLoading = true

            try {
                val response = scheduleRepository.getAllSchedulesMonthByUser(
                    token = token,
                    id = id,
                    month = dateTime
                )

                if (response.isSuccessful) {
                    val schedules = response.body()
                    val filteredSchedules = schedules
                        ?.filter { it.deletedAt == null }
                        ?.sortedBy { LocalDateTime.parse(it.scheduleDate) }
                        ?: emptyList()
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

    fun reviewScheduleByID(token: String, idAgendamento: Int, nota: Int, id: Int, dateTime: LocalDateTime) {

        viewModelScope.launch {
            isLoading = true

            try {
                val response = scheduleRepository.reviewScheduleByID(token, idAgendamento, nota)

                if (response.isSuccessful) {
                    val novoScheduleItem = response.body()
                    _scheduleItem.value = novoScheduleItem

                    getAllSchedulesMonthByUser(token, id, dateTime)
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

data class PetResumo (
    val id: Int,
    val name: String
)