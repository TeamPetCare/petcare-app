package com.example.petcare_app.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.dto.PlanInfoDTO
import com.example.petcare_app.data.network.RetrofitInstance
import com.example.petcare_app.data.repository.PlanRepository
import com.example.petcare_app.data.services.PlanService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PlanViewModel : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    private val _allPlansUser = MutableStateFlow<List<PlanInfoDTO>>(emptyList())
    val allPlansUser: StateFlow<List<PlanInfoDTO>> = _allPlansUser

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val repository = object : PlanRepository {
        override val api: PlanService = RetrofitInstance.retrofit.create(PlanService::class.java)
    }

    private fun getPlansByUserId(token: String, userId: Int) {
        viewModelScope.launch {
            isLoading = true
            _error.value = null

            try {
                val response = repository.getPlansByUserId(token, userId)

                if (response.isSuccessful) {
                    val plans = response.body()

                    val activePlans = plans?.filter { it.active } ?: emptyList()

                    _allPlansUser.value = activePlans

                    Log.d("PLAN_SUCESS", "Planos carregados: ${activePlans.size}")
                } else {
                    val errorMessage = "Erro ao buscar planos: ${response.code()}"
                    _error.value = errorMessage
                    Log.d("API_ERROR", "Erro body: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                val errorMessage = "Erro de conexão ${e.message}"
                _error.value = errorMessage
                Log.e("API_EXCEPTION_PLANS", errorMessage, e)
            }

            isLoading = false
        }
    }

    fun getAllPlansByUserId(token: String, userId: Int) {
        viewModelScope.launch {
            isLoading = true
            _error.value = null

            try {
                val response = repository.getPlansByUserId(token, userId)

                if (response.isSuccessful) {
                    val plans = response.body() ?: emptyList()
                    _allPlansUser.value = plans

                    Log.d("PLAN_SUCCESS", "Todos os planos carregados: ${plans.size}")
                } else {
                    val errorMessage = "Erro ao buscar todos os planos: ${response.code()}"
                    _error.value = errorMessage
                    Log.d("API_ERROR", "Erro body: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                val errorMessage = "Erro de conexão: ${e.message}"
                _error.value = errorMessage
                Log.e("API_EXCEPTION_ALL_PLANS", errorMessage, e)
            }

            isLoading = false
        }
    }

    fun clearError() {
        _error.value = null
    }

    fun refreshPlans(token: String, userId: Int) {
        getPlansByUserId(token, userId)
    }

    fun hasActivePlans(): Boolean {
        return _allPlansUser.value.any { it.active }
    }

    fun getActivePlans(): List<PlanInfoDTO> {
        return _allPlansUser.value.filter { it.active }
    }

    fun getInactivePlans(): List<PlanInfoDTO> {
        return _allPlansUser.value.filter { !it.active }
    }

}