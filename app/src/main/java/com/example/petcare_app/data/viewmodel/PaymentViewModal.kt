package com.example.petcare_app.data.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.model.PaymentResponse
import com.example.petcare_app.data.repository.PaymentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class PaymentUiState(
    val isLoading: Boolean = false,
    val paymentData: PaymentResponse? = null,
    val errorMessage: String? = null
)

class PaymentViewModel : ViewModel() {

    private val repository = PaymentRepository()

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState.asStateFlow()

    fun createPixPayment(userId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                val response = repository.createPixPayment(userId)

                if (response.isSuccessful) {
                    response.body()?.let { paymentData ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            paymentData = paymentData
                        )
                    }
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = "Erro ao processar pagamento: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "Erro de conexão: ${e.localizedMessage}"
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }
}