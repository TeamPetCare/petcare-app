package com.example.petcare_app.data.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petcare_app.data.dto.PetByUserIdDTO
import com.example.petcare_app.data.dto.ScheduleCreateDTO
import com.example.petcare_app.data.model.Pet
import com.example.petcare_app.data.model.Schedule
import com.example.petcare_app.data.model.Services
import com.example.petcare_app.data.model.User
import com.example.petcare_app.data.model.PixPaymentRequest
import com.example.petcare_app.data.model.PixPaymentResponse
import com.example.petcare_app.data.network.RetrofitInstance
import com.example.petcare_app.data.services.PetService
import com.example.petcare_app.data.services.ScheduleService
import com.example.petcare_app.data.services.UserService
import com.example.petcare_app.data.services.PaymentService
import com.example.petcare_app.ui.components.dialogs.createSchedule.ScheduleFormData
import com.example.petcare_app.utils.DateTimeUtils
import com.example.petcare_app.data.model.ScheduleStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class CreateScheduleViewModel : ViewModel() {
    var isLoading by mutableStateOf(false)
        private set

    var isLoadingData by mutableStateOf(false)
        private set

    // Estado do agendamento criado
    private val _createdSchedule = MutableStateFlow<Schedule?>(null)
    val createdSchedule: StateFlow<Schedule?> = _createdSchedule

    // Estados do fluxo
    var showScheduleFlow by mutableStateOf(false)
        private set
    var currentStep by mutableStateOf(CreateScheduleStep.FORM)
        private set

    // Dados do formulário atual
    var currentFormData by mutableStateOf<ScheduleFormData?>(null)
        private set

    // Controla se o dialog original deve fechar
    var shouldCloseOriginalDialog by mutableStateOf(false)
        private set

    // Estados do pagamento PIX
    var pixPaymentResponse by mutableStateOf<PixPaymentResponse?>(null)
        private set

    // Dados carregados da API
    private val _userPets = MutableStateFlow<List<PetByUserIdDTO>>(emptyList())
    val userPets: StateFlow<List<PetByUserIdDTO>> = _userPets.asStateFlow()

    private val _availableServices = MutableStateFlow<List<Services>>(emptyList())
    val availableServices: StateFlow<List<Services>> = _availableServices.asStateFlow()

    private val _availableEmployees = MutableStateFlow<List<User>>(emptyList())
    val availableEmployees: StateFlow<List<User>> = _availableEmployees.asStateFlow()

    // Estados de erro
    var errorMessage by mutableStateOf<String?>(null)
        private set

    fun loadInitialData(token: String, userId: Int) {
        viewModelScope.launch {
            isLoadingData = true
            errorMessage = null

            try {
                // Carregar pets do usuário
                loadUserPets(token, userId)
                
                // Carregar serviços disponíveis
                loadAvailableServices(token)
                
                // Carregar funcionários disponíveis
                loadAvailableEmployees(token)

            } catch (e: Exception) {
                Log.e("CreateScheduleViewModel", "Erro ao carregar dados iniciais", e)
                errorMessage = "Erro ao carregar dados. Tente novamente."
            } finally {
                isLoadingData = false
            }
        }
    }

    private suspend fun loadUserPets(token: String, userId: Int) {
        try {
            Log.d("CreateScheduleViewModel", "🐕 Carregando pets do usuário ID: $userId")
            val petService = RetrofitInstance.retrofit.create(PetService::class.java)
            val response = petService.getPetsByUserId("Bearer $token", userId)
            
            Log.d("CreateScheduleViewModel", "🐕 Resposta pets - Código: ${response.code()}")
            
            if (response.isSuccessful) {
                _userPets.value = response.body() ?: emptyList()
                Log.d("CreateScheduleViewModel", "✅ Pets carregados: ${_userPets.value.size}")
                _userPets.value.forEach { pet ->
                    Log.d("CreateScheduleViewModel", "Pet: ID=${pet.id}, Nome=${pet.name}")
                }
            } else {
                Log.e("CreateScheduleViewModel", "❌ Erro ao carregar pets: ${response.code()}")
                Log.e("CreateScheduleViewModel", "❌ Body: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("CreateScheduleViewModel", "❌ Erro na requisição de pets", e)
        }
    }

    private suspend fun loadAvailableServices(token: String) {
        try {
            Log.d("CreateScheduleViewModel", "🔍 Iniciando carregamento de serviços...")
            Log.d("CreateScheduleViewModel", "🔑 Token completo: $token")
            Log.d("CreateScheduleViewModel", "🔑 Header que será enviado: Bearer $token")
            
            val planService = RetrofitInstance.retrofit.create(com.example.petcare_app.data.services.PlanService::class.java)
            Log.d("CreateScheduleViewModel", "📡 Fazendo requisição GET para /services")
            Log.d("CreateScheduleViewModel", "🌐 URL completa: http://44.217.106.6/api/services")
            
            val response = planService.getServices("Bearer $token")
            
            Log.d("CreateScheduleViewModel", "📥 Resposta recebida - Código: ${response.code()}")
            Log.d("CreateScheduleViewModel", "📥 Resposta bem-sucedida: ${response.isSuccessful}")
            
            if (response.isSuccessful) {
                val services = response.body() ?: emptyList()
                Log.d("CreateScheduleViewModel", "📦 Raw services from API: ${services.size}")
                services.forEachIndexed { index, service ->
                    Log.d("CreateScheduleViewModel", "[$index] Raw Service: ID=${service.id}, Nome='${service.name ?: "null"}', Preço=${service.price}, Note='${service.note ?: "null"}', EstimatedTime='${service.estimatedTime ?: "null"}', Disponível=${service.disponibility}")
                }
                
                // Filtrar serviços válidos para evitar problemas
                val validServices = services.map { service ->
                    // Corrigir problema: API está enviando nome no campo 'note' em vez de 'name'
                    val serviceName = if (service.name.isNullOrBlank()) {
                        Log.d("CreateScheduleViewModel", "🔧 Corrigindo nome: usando note '${service.note}' como nome do serviço ID=${service.id}")
                        service.note?.takeIf { it.isNotBlank() } ?: "Serviço sem nome"
                    } else {
                        service.name!!
                    }
                    
                    service.copy(name = serviceName)
                }.filter { service ->
                    try {
                        !service.name.isNullOrBlank() && service.price >= 0
                    } catch (e: Exception) {
                        Log.e("CreateScheduleViewModel", "❌ Erro ao validar serviço: ${service}", e)
                        false
                    }
                }
                
                _availableServices.value = validServices
                Log.d("CreateScheduleViewModel", "✅ Serviços válidos carregados: ${_availableServices.value.size}")
                _availableServices.value.forEach { service ->
                    Log.d("CreateScheduleViewModel", "✅ Serviço corrigido: ID=${service.id}, Nome='${service.name}', Preço=${service.price}, Note='${service.note ?: "N/A"}', Disponível=${service.disponibility}")
                }
            } else {
                Log.e("CreateScheduleViewModel", "❌ ERRO 403 - FORBIDDEN!")
                Log.e("CreateScheduleViewModel", "❌ Código HTTP: ${response.code()}")
                Log.e("CreateScheduleViewModel", "❌ Mensagem: ${response.message()}")
                Log.e("CreateScheduleViewModel", "❌ Headers da resposta: ${response.headers()}")
                val errorBody = response.errorBody()?.string()
                Log.e("CreateScheduleViewModel", "❌ Body do erro: $errorBody")
                
                // Verificar se é problema de token
                if (response.code() == 403) {
                    errorMessage = "Acesso negado. Token pode estar expirado ou inválido."
                } else {
                    errorMessage = "Erro ao carregar serviços da API (${response.code()})"
                }
            }
        } catch (e: Exception) {
            Log.e("CreateScheduleViewModel", "❌ Exceção na requisição de serviços", e)
            Log.e("CreateScheduleViewModel", "Tipo de erro: ${e.javaClass.simpleName}")
            Log.e("CreateScheduleViewModel", "Mensagem: ${e.message}")
            errorMessage = "Erro de conexão ao carregar serviços: ${e.message}"
        }
    }

    private suspend fun loadAvailableEmployees(token: String) {
        try {
            Log.d("CreateScheduleViewModel", "👥 Iniciando carregamento de funcionários...")
            Log.d("CreateScheduleViewModel", "🔑 Token: ${token.take(10)}...")
            
            val userService = RetrofitInstance.retrofit.create(UserService::class.java)
            Log.d("CreateScheduleViewModel", "📡 Fazendo requisição GET para /users/employees")
            Log.d("CreateScheduleViewModel", "🌐 URL completa: http://44.217.106.6/api/users/employees")
            Log.d("CreateScheduleViewModel", "🔑 Header Authorization: Bearer ${token.take(10)}...")
            
            val response = userService.getEmployees("Bearer $token")
            
            Log.d("CreateScheduleViewModel", "📥 Resposta recebida - Código: ${response.code()}")
            Log.d("CreateScheduleViewModel", "📥 Resposta bem-sucedida: ${response.isSuccessful}")
            
            if (response.isSuccessful) {
                val employees = response.body() ?: emptyList()
                _availableEmployees.value = employees
                Log.d("CreateScheduleViewModel", "✅ Funcionários carregados: ${employees.size}")
                employees.forEachIndexed { index, employee ->
                    Log.d("CreateScheduleViewModel", "[$index] Funcionário: ID=${employee.id}, Nome='${employee.name}', UserType='${""}', Ativo=${""}")
                }
                
                if (employees.isEmpty()) {
                    Log.w("CreateScheduleViewModel", "⚠️ Nenhum funcionário foi retornado da API!")
                }
            } else {
                Log.e("CreateScheduleViewModel", "❌ ERRO ${response.code()} ao carregar funcionários!")
                Log.e("CreateScheduleViewModel", "❌ Mensagem: ${response.message()}")
                Log.e("CreateScheduleViewModel", "❌ Headers da resposta: ${response.headers()}")
                val errorBody = response.errorBody()?.string()
                Log.e("CreateScheduleViewModel", "❌ Body do erro: $errorBody")
                
                if (response.code() == 403) {
                    Log.e("CreateScheduleViewModel", "❌ Erro 403 - Token pode estar expirado ou sem permissão")
                }
            }
        } catch (e: Exception) {
            Log.e("CreateScheduleViewModel", "❌ Exceção na requisição de funcionários", e)
            Log.e("CreateScheduleViewModel", "Tipo de erro: ${e.javaClass.simpleName}")
            Log.e("CreateScheduleViewModel", "Mensagem: ${e.message}")
        }
    }

    fun startScheduleFlow(formData: ScheduleFormData) {
        Log.d("CreateScheduleViewModel", "🚀 Iniciando fluxo de agendamento!")
        Log.d("CreateScheduleViewModel", "📋 Dados recebidos:")
        Log.d("CreateScheduleViewModel", "Pet: ${formData.pet.name} (ID: ${formData.pet.id})")
        Log.d("CreateScheduleViewModel", "Serviços: ${formData.services.joinToString { "${it.name} (ID: ${it.id})" }}")
        Log.d("CreateScheduleViewModel", "Data/Hora: ${formData.date} ${formData.time}")
        Log.d("CreateScheduleViewModel", "Funcionário: ${formData.employee?.name ?: "NENHUM"} (ID: ${formData.employee?.id ?: "NULL"})")
        Log.d("CreateScheduleViewModel", "Pagamento: ${formData.paymentMethod.displayName}")
        
        currentFormData = formData
        shouldCloseOriginalDialog = true
        showScheduleFlow = true
        currentStep = CreateScheduleStep.PAYMENT // Começa na tela de pagamento
        
        Log.d("CreateScheduleViewModel", "✅ Estado atualizado - showScheduleFlow: $showScheduleFlow")
    }

    fun hideScheduleFlow() {
        showScheduleFlow = false
        shouldCloseOriginalDialog = false
        resetForm()
    }

    fun nextStep() {
        currentStep = when (currentStep) {
            CreateScheduleStep.FORM -> CreateScheduleStep.PAYMENT
            CreateScheduleStep.PAYMENT -> CreateScheduleStep.PAYMENT // Agendamento será criado no PAYMENT
            CreateScheduleStep.PIX_PAYMENT -> CreateScheduleStep.SUCCESS
            CreateScheduleStep.CASH_CONFIRMATION -> CreateScheduleStep.SUCCESS
            CreateScheduleStep.SUCCESS -> CreateScheduleStep.SUCCESS
        }
    }

    fun previousStep() {
        currentStep = when (currentStep) {
            CreateScheduleStep.PAYMENT -> {
                // Volta para o formulário (fecha o fluxo e reabre o dialog original)
                hideScheduleFlow()
                CreateScheduleStep.FORM
            }
            CreateScheduleStep.PIX_PAYMENT -> CreateScheduleStep.PAYMENT
            CreateScheduleStep.CASH_CONFIRMATION -> CreateScheduleStep.PAYMENT
            CreateScheduleStep.SUCCESS -> {
                // Não deve voltar do sucesso
                CreateScheduleStep.SUCCESS
            }
            CreateScheduleStep.FORM -> CreateScheduleStep.FORM
        }
    }

    fun processPayment() {
        nextStep() // Move para confirmação
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createSchedule(token: String, userId: Int) {
        val formData = currentFormData ?: return

        // Converter data e hora para formato ISO usando utilitários
        val scheduleDateTime = DateTimeUtils.convertToISODateTime(formData.date, formData.time)
        if (scheduleDateTime == null) {
            Log.e("CreateScheduleViewModel", "Erro ao converter data/hora: ${formData.date} ${formData.time}")
            errorMessage = "Data ou hora inválida. Verifique os dados informados."
            return
        }

        val creationDateTime = DateTimeUtils.getCurrentISODateTime()
        val estimatedDuration = DateTimeUtils.getEstimatedDuration(formData.services.map { it.name })

        val scheduleCreateDTO = ScheduleCreateDTO(
            scheduleStatus = ScheduleStatus.AGENDADO.status,
            scheduleDate = scheduleDateTime,
            scheduleTime = estimatedDuration, // Duração estimada baseada nos serviços
            creationDate = creationDateTime,
            scheduleNote = if (formData.notes.isBlank()) null else formData.notes,
            petId = formData.pet.id,
            paymentId = 3, // Payment será null inicialmente
            serviceIds = formData.services.map { it.id },
            employeeId = formData.employee?.id,
            deletedAt = null
        )

        Log.d("CreateScheduleViewModel", "📦 DTO criado com os seguintes dados:")
        Log.d("CreateScheduleViewModel", "scheduleStatus: ${scheduleCreateDTO.scheduleStatus}")
        Log.d("CreateScheduleViewModel", "scheduleDate: ${scheduleCreateDTO.scheduleDate}")
        Log.d("CreateScheduleViewModel", "scheduleTime: ${scheduleCreateDTO.scheduleTime}")
        Log.d("CreateScheduleViewModel", "creationDate: ${scheduleCreateDTO.creationDate}")
        Log.d("CreateScheduleViewModel", "petId: ${scheduleCreateDTO.petId}")
        Log.d("CreateScheduleViewModel", "serviceIds: ${scheduleCreateDTO.serviceIds}")
        Log.d("CreateScheduleViewModel", "employeeId: ${scheduleCreateDTO.employeeId} (${if (scheduleCreateDTO.employeeId == null) "❌ NULL!" else "✅ Definido"})")
        Log.d("CreateScheduleViewModel", "paymentId: ${scheduleCreateDTO.paymentId}")
        Log.d("CreateScheduleViewModel", "scheduleNote: ${scheduleCreateDTO.scheduleNote}")
        Log.d("CreateScheduleViewModel", "🚀 Enviando para API...")

        val api = RetrofitInstance.retrofit.create(ScheduleService::class.java)

        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            try {
                val response = api.createSchedule("Bearer $token", scheduleCreateDTO)

                if (response.isSuccessful) {
                    _createdSchedule.value = response.body()
                    Log.d("CreateScheduleViewModel", "✅ Agendamento criado com sucesso!")
                    
                    // Decidir próximo passo baseado no método de pagamento
                    when (formData.paymentMethod.id) {
                        "pix" -> {
                            // Para PIX: processar pagamento automaticamente
                            Log.d("CreateScheduleViewModel", "💳 Processando pagamento PIX automaticamente...")
                            processPixPayment(token, userId)
                            currentStep = CreateScheduleStep.PIX_PAYMENT
                        }
                        "dinheiro" -> {
                            // Para dinheiro: vai para tela de confirmação presencial
                            currentStep = CreateScheduleStep.CASH_CONFIRMATION
                        }
                        else -> {
                            // Default: vai para sucesso
                            currentStep = CreateScheduleStep.SUCCESS
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("CreateScheduleViewModel", "Erro ao criar agendamento: $errorBody")
                    errorMessage = "Erro ao criar agendamento. Tente novamente."
                }
            } catch (e: Exception) {
                Log.e("CreateScheduleViewModel", "Erro de conexão ao criar agendamento", e)
                errorMessage = "Erro de conexão. Verifique sua internet."
            }

//            isLoading = false
        }
    }

    fun clearError() {
        errorMessage = null
    }

    // Método para processar pagamento PIX com dados do usuário
    fun processPixPayment(token: String, userId: Int) {

        val formData = currentFormData ?: return
        
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            
            try {
                // Obter dados do usuário primeiro
                val userService = RetrofitInstance.retrofit.create(UserService::class.java)
                val userResponse = userService.getUserById("Bearer $token", userId)
                
                if (!userResponse.isSuccessful) {
                    errorMessage = "Erro ao obter dados do usuário"
                    isLoading = false
                    return@launch
                }
                
                val user = userResponse.body()
                if (user == null) {
                    errorMessage = "Dados do usuário não encontrados"
                    isLoading = false
                    return@launch
                }
                
                // Calcular o valor total dos serviços selecionados
                val totalAmount = formData.services.sumOf { it.price }
                
                // Criar o request PIX com os dados do usuário
                val pixRequest = PixPaymentRequest(
                    amount = totalAmount,
                    email = user.email,
                    name = user.name,
                    cpf = user.cpfClient
                )
                
                Log.d("CreateScheduleViewModel", "💰 Processando pagamento PIX:")
                Log.d("CreateScheduleViewModel", "Amount: ${pixRequest.amount}")
                Log.d("CreateScheduleViewModel", "Email: ${pixRequest.email}")
                Log.d("CreateScheduleViewModel", "Name: ${pixRequest.name}")
                Log.d("CreateScheduleViewModel", "CPF: ${pixRequest.cpf}")
                
                // Fazer a requisição para a API de pagamento PIX
                val paymentService = RetrofitInstance.retrofit.create(PaymentService::class.java)
                val paymentResponse = paymentService.createPixPayment("Bearer $token", pixRequest)
                
                if (paymentResponse.isSuccessful) {
                    pixPaymentResponse = paymentResponse.body()
                    Log.d("CreateScheduleViewModel", "✅ Pagamento PIX processado com sucesso!")
                } else {
                    val errorBody = paymentResponse.errorBody()?.string()
                    Log.e("CreateScheduleViewModel", "❌ Erro no pagamento PIX: $errorBody")
                    errorMessage = "Erro ao processar pagamento PIX: ${paymentResponse.message()}"
                }
                
            } catch (e: Exception) {
                Log.e("CreateScheduleViewModel", "❌ Erro na requisição PIX", e)
                errorMessage = "Erro de conexão ao processar pagamento: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    private fun resetForm() {
        currentFormData = null
        _createdSchedule.value = null
        currentStep = CreateScheduleStep.FORM
        errorMessage = null
    }
}

enum class CreateScheduleStep {
    FORM,
    PAYMENT,
    PIX_PAYMENT,
    CASH_CONFIRMATION,
    SUCCESS
}