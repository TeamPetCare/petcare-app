package com.example.petcare_app.data.repository

<<<<<<< HEAD
import com.example.petcare_app.data.dto.ScheduleDTO
import com.example.petcare_app.data.dto.SchedulePUTDTO
=======
import com.example.petcare_app.data.dto.ScheduleCreateDTO
>>>>>>> dea19d2e9e37c5016d7bd519afc6a75f3ecdb48f
import com.example.petcare_app.data.model.Schedule
import com.example.petcare_app.data.services.ScheduleService
import retrofit2.Response
import java.time.LocalDateTime

<<<<<<< HEAD
interface ScheduleRepository {
    val api : ScheduleRepository

    suspend fun getAllSchedulesByUser(
        token: String,
        id: Int
    ) : Response<List<ScheduleDTO>> {
        return api.getAllSchedulesByUser(token, id)
    }

=======
class ScheduleRepository(private val api: ScheduleService) {
    
>>>>>>> dea19d2e9e37c5016d7bd519afc6a75f3ecdb48f
    suspend fun getAllSchedulesMonthByUser(
        token: String, 
        id: Int, 
        month: LocalDateTime
    ): Response<List<Schedule>> {
        return api.getAllSchedulesMonthByUser(token, id, month)
    }

    suspend fun createSchedule(
        token: String, 
        scheduleCreateDTO: ScheduleCreateDTO
    ): Response<Schedule> {
        return api.createSchedule(token, scheduleCreateDTO)
    }
    
    suspend fun getAllSchedulesMonthByPet(
        token: String, 
        id: Int, 
        date: LocalDateTime
    ): Response<List<Schedule>> {
        return api.getAllSchedulesMonthByPet(token, id, date)
    }

    suspend fun reviewScheduleByID(
        token: String,
        id: Int,
        nota: Int
    ) : Response<SchedulePUTDTO> {
        return api.reviewScheduleByID(token, id, nota)
    }
}