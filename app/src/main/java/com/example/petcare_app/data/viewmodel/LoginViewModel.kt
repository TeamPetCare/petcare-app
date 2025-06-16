package com.example.petcare_app.data.viewmodel

import TokenDataStore
import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.model.LoginRequest
import com.example.petcare_app.data.network.RetrofitInstance
import com.example.petcare_app.data.repository.LoginRepository
import com.example.petcare_app.data.services.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val loginRepository: LoginRepository,
    private val dataStore: TokenDataStore
) : ViewModel() {
    private val _loginState = MutableStateFlow<Result<Unit>?>(null)
    val loginState: StateFlow<Result<Unit>?> = _loginState

    var isLoading by mutableStateOf(false)
        private set


    fun login(email: String, password: String) {
        isLoading = true
        viewModelScope.launch {
            try {
                Log.d("LOGIN_DEBUG", "Iniciando login com email: $email")

                val response = loginRepository.login(email, password)

                Log.d("LOGIN_DEBUG", "Response code: ${response.code()}")
                Log.d("LOGIN_DEBUG", "Response body: ${response.body()}")

                if (response.isSuccessful) {
                    val token = response.body()?.token
                    val name = response.body()?.name
                    val id = response.body()?.id
                    if (!token.isNullOrEmpty()) {
                        dataStore.saveUserInfo(token, name ?: "", id ?: 0)
                        _loginState.value = Result.success(Unit)

                        Log.d("LOGIN_DEBUG", "RGet Token: ${dataStore.getToken}")

                    } else {
                        _loginState.value = Result.failure(Exception("Token vazio"))
                    }
                } else {
                    _loginState.value = Result.failure(Exception("Erro na autenticação"))
                }
            } catch (e: Exception) {
                _loginState.value = Result.failure(e)
            }
            isLoading = false
        }
    }
}
