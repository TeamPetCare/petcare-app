package com.example.petcare_app.data.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.network.RetrofitInstance
import com.example.petcare_app.data.repository.CpfValidationRepository
import com.example.petcare_app.data.services.CpfValidationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CpfValidationViewModel(
    private val cpfValidationRepository: CpfValidationRepository
) : ViewModel() {
    private val _isLoading = mutableStateOf(false)
    val isLoading: Boolean get() = _isLoading.value

    private val _cpfValidate = MutableStateFlow<Result<Boolean>?>(null)
    val cpfValidate: StateFlow<Result<Boolean>?> = _cpfValidate

    fun setIsLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun validateCpf(cpf: String) {
        Log.d("CpfValidation", "Iniciando validação do CPF: $cpf")

        viewModelScope.launch {
            _isLoading.value = true
            Log.d("CpfValidation", "isLoading = true")

            try {
                val response = cpfValidationRepository.validateCpf(cpf)
                Log.d("CpfValidation", "Resposta recebida: isSuccessful=${response.isSuccessful}, body=${response.body()}")

                if (response.isSuccessful) {
                    val isValid = response.body() ?: false
                    _cpfValidate.value = Result.success(isValid)
                    Log.d("CpfValidation", "Validação bem-sucedida: isValid=$isValid")
                } else {
                    _cpfValidate.value = Result.failure(Exception("Erro: ${response.body()}"))
                    Log.d("CpfValidation", "Erro na validação: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                _cpfValidate.value = Result.failure(e)
                Log.e("CpfValidation", "Exceção durante a validação", e)
            }

            _isLoading.value = false
            Log.d("CpfValidation", "isLoading = false")
        }
    }
}