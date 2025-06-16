package com.example.petcare_app.data.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.network.RetrofitInstance
import com.example.petcare_app.data.repository.LoginRepository
import com.example.petcare_app.data.services.LoginService
import kotlinx.coroutines.launch

class VerifyTokenViewModel(
    private val loginRepository: LoginRepository
) : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    var isTokenValid by mutableStateOf<Boolean?>(null)

    fun verifyToken(token: String) {
        isLoading = true

        viewModelScope.launch {
            try {
                val response = loginRepository.verifyToken(token)
                val responseBody = response.body()?.string()

                if (response.isSuccessful) {
                    if (responseBody != null && responseBody.contains("Token válido")) {
                        isTokenValid = true
                    } else {
                        isTokenValid = false
                    }
                } else {
                    Log.d("VerifyTokenViewModel", "Erro na resposta: ${response.message()}")
                    isTokenValid = false
                }
            } catch (e: Exception) {
                isTokenValid = false
                Log.e("VerifyTokenViewModel", "Erro ao verificar token", e)
            }
            isLoading = false
        }
    }
}