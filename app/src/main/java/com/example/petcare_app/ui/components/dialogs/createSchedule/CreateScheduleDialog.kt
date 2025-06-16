package com.example.petcare_app.ui.components.dialogs.createSchedule

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petcare_app.data.dto.PetByUserIdDTO
import com.example.petcare_app.data.model.Services
import com.example.petcare_app.data.model.User
import com.example.petcare_app.data.viewmodel.CreateScheduleViewModel
import com.example.petcare_app.datastore.TokenDataStore
import com.example.petcare_app.ui.components.layouts.LoadingBar
import com.example.petcare_app.ui.theme.buttonTextStyle
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.errorMessage
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle
import com.example.petcare_app.ui.components.formFields.inputFields.DateInputField
import com.example.petcare_app.ui.components.formFields.inputFields.TimeInputField
import com.example.petcare_app.utils.DateTimeUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScheduleDialog(
    onConfirm: (ScheduleFormData) -> Unit = {},
    setOpenCreateScheduleDialog: (Boolean) -> Unit
) {
    val context = LocalContext.current
    val dataStore = TokenDataStore.getInstance(context)
    val viewModel: CreateScheduleViewModel = viewModel()

    // Estados do DataStore
    val token by dataStore.getToken.collectAsState(initial = null)
    val userId by dataStore.getId.collectAsState(initial = null)

    // Estados do ViewModel
    val userPets by viewModel.userPets.collectAsState()
    val availableServices by viewModel.availableServices.collectAsState()
    val availableEmployees by viewModel.availableEmployees.collectAsState()
    


    // Estados locais do formulário
    var selectedPet by remember { mutableStateOf<PetByUserIdDTO?>(null) }
    var selectedServices by remember { mutableStateOf<List<Services>>(emptyList()) }
    var selectedEmployee by remember { mutableStateOf<User?>(null) }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var selectedPaymentMethod by remember { mutableStateOf<PaymentMethod?>(null) }
    var notes by remember { mutableStateOf("") }
    var totalPrice by remember { mutableStateOf(0.0) }

    // Estados de UI
    var showPetDropdown by remember { mutableStateOf(false) }
    var showServiceDropdown by remember { mutableStateOf(false) }
    var showEmployeeDropdown by remember { mutableStateOf(false) }
    var showPaymentDropdown by remember { mutableStateOf(false) }
    var isFormSubmitted by remember { mutableStateOf(false) }

    // Lista de métodos de pagamento
    val paymentMethods = listOf(
        PaymentMethod("dinheiro", "Dinheiro"),
        PaymentMethod("pix", "PIX"),
    )

    // Carregar dados iniciais
    LaunchedEffect(token, userId) {
        Log.d("CreateScheduleDialog", "🚀 Iniciando carregamento de dados. Token: ${token?.take(10)}..., UserId: $userId")
        if (token != null && userId != null) {
            viewModel.loadInitialData(token!!, userId!!)
        } else {
            Log.e("CreateScheduleDialog", "❌ Token ou UserId são null!")
        }
    }

    // Log quando os funcionários mudarem
    LaunchedEffect(availableEmployees) {
        Log.d("CreateScheduleDialog", "👥 Funcionários disponíveis atualizados: ${availableEmployees.size}")
        availableEmployees.forEachIndexed { index, employee ->
            Log.d("CreateScheduleDialog", "[$index] Funcionário UI: ID=${employee.id}, Nome='${employee.name}', UserType='${""}'")
        }
        if (availableEmployees.isEmpty()) {
            Log.w("CreateScheduleDialog", "⚠️ Nenhum funcionário disponível no UI!")
        }
    }

    // Calcular preço total quando serviços mudam
    LaunchedEffect(selectedServices) {
        Log.d("CreateScheduleDialog", "💰 Calculando preço total para ${selectedServices.size} serviços")
        totalPrice = selectedServices.sumOf { service ->
            try {
                service.price
            } catch (e: Exception) {
                Log.e("CreateScheduleDialog", "❌ Erro ao acessar preço do serviço: ${service.name}", e)
                0.0
            }
        }
        Log.d("CreateScheduleDialog", "✅ Preço total calculado: R$ $totalPrice")
    }

    // Função para validar o formulário
    fun validateForm(): Boolean {
        val petValid = selectedPet != null
        val servicesValid = selectedServices.isNotEmpty()
        val dateValid = DateTimeUtils.validateDate(selectedDate)
        val timeValid = DateTimeUtils.validateTime(selectedTime)
        val paymentValid = selectedPaymentMethod != null
        
        Log.d("CreateScheduleDialog", "🔍 Validação detalhada:")
        Log.d("CreateScheduleDialog", "Pet válido: $petValid")
        Log.d("CreateScheduleDialog", "Serviços válidos: $servicesValid")
        Log.d("CreateScheduleDialog", "Data válida: $dateValid (valor: '$selectedDate')")
        Log.d("CreateScheduleDialog", "Hora válida: $timeValid (valor: '$selectedTime')")
        Log.d("CreateScheduleDialog", "Pagamento válido: $paymentValid")
        
        return petValid && servicesValid && dateValid && timeValid && paymentValid
    }

    Dialog(
        onDismissRequest = { setOpenCreateScheduleDialog(false) },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp, bottomStart = 16.dp, bottomEnd = 16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 700.dp)
                        .padding(20.dp)
                ) {
                    // Título
                    Text(
                        text = "Agendar Atendimento",
                        style = sentenceTitleTextStyle,
                        color = customColorScheme.primary,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    // Mostrar loading se dados estão carregando
                    if (viewModel.isLoadingData) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingBar()
                        }
                    } else {
                        // Mostrar erro se houver
                        viewModel.errorMessage?.let { error ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                colors = CardDefaults.cardColors(containerColor = errorMessage.copy(alpha = 0.1f))
                            ) {
                                Text(
                                    text = error,
                                    color = errorMessage,
                                    modifier = Modifier.padding(12.dp),
                                    fontSize = 14.sp
                                )
                            }
                        }

                        // Mostrar erros de validação
                        if (isFormSubmitted && !validateForm()) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                colors = CardDefaults.cardColors(containerColor = customColorScheme.error.copy(alpha = 0.1f))
                            ) {
                                Column(
                                    modifier = Modifier.padding(12.dp)
                                ) {
                                    Text(
                                        text = "Por favor, corrija os seguintes campos:",
                                        color = customColorScheme.error,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    if (selectedPet == null) {
                                        Text("• Selecione um pet", color = customColorScheme.error, fontSize = 12.sp)
                                    }
                                    if (selectedServices.isEmpty()) {
                                        Text("• Selecione pelo menos um serviço", color = customColorScheme.error, fontSize = 12.sp)
                                    }
                                    if (!DateTimeUtils.validateDate(selectedDate)) {
                                        Text("• Informe uma data válida (dd/mm/yyyy)", color = customColorScheme.error, fontSize = 12.sp)
                                    }
                                    if (!DateTimeUtils.validateTime(selectedTime)) {
                                        Text("• Informe um horário válido (HH:mm)", color = customColorScheme.error, fontSize = 12.sp)
                                    }
                                    if (selectedPaymentMethod == null) {
                                        Text("• Selecione a forma de pagamento", color = customColorScheme.error, fontSize = 12.sp)
                                    }
                                }
                            }
                        }

                        // Conteúdo com scroll
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .verticalScroll(rememberScrollState())
                        ) {
                            // Dropdown Pet
                            Text(
                                text = "Qual pet será atendido?*",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Box {
                                OutlinedCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { 
                                            if (userPets.isNotEmpty()) {
                                                showPetDropdown = !showPetDropdown 
                                            }
                                        },
                                    colors = CardDefaults.outlinedCardColors(
                                        containerColor = if (isFormSubmitted && selectedPet == null)
                                            customColorScheme.error.copy(alpha = 0.1f)
                                        else Color.White
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = selectedPet?.name ?: if (userPets.isEmpty()) "Nenhum pet cadastrado" else "Selecione o pet",
                                            color = if (selectedPet != null) Color.Black else Color.Gray
                                        )
                                        if (userPets.isNotEmpty()) {
                                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                                        }
                                    }
                                }

                                DropdownMenu(
                                    expanded = showPetDropdown,
                                    onDismissRequest = { showPetDropdown = false }
                                ) {
                                    userPets.forEach { pet ->
                                        DropdownMenuItem(
                                            text = { Text(pet.name) },
                                            onClick = {
                                                selectedPet = pet
                                                showPetDropdown = false
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Dropdown Serviços
                            Text(
                                text = "Escolha o(s) serviço(s) desejado(s)*",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Box {
                                OutlinedCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { 
                                            Log.d("CreateScheduleDialog", "🔍 Clicou no dropdown de serviços. Services disponíveis: ${availableServices.size}")
                                            availableServices.forEach { service ->
                                                Log.d("CreateScheduleDialog", "Service: ID=${service.id}, Nome=${service.name ?: "null"}, Preço=${service.price}")
                                            }
                                            if (availableServices.isNotEmpty()) {
                                                showServiceDropdown = !showServiceDropdown 
                                            }
                                        },
                                    colors = CardDefaults.outlinedCardColors(
                                        containerColor = if (isFormSubmitted && selectedServices.isEmpty())
                                            customColorScheme.error.copy(alpha = 0.1f)
                                        else Color.White
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = if (selectedServices.isNotEmpty())
                                                "${selectedServices.size} serviço(s) selecionado(s)"
                                            else if (availableServices.isEmpty()) "Nenhum serviço disponível" 
                                            else "Selecione os serviços",
                                            color = if (selectedServices.isNotEmpty()) Color.Black else Color.Gray
                                        )
                                        if (availableServices.isNotEmpty()) {
                                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                                        }
                                    }
                                }

                                DropdownMenu(
                                    expanded = showServiceDropdown,
                                    onDismissRequest = { 
                                        Log.d("CreateScheduleDialog", "🔍 Fechando dropdown de serviços")
                                        showServiceDropdown = false 
                                    }
                                ) {
                                    Log.d("CreateScheduleDialog", "🔍 Renderizando dropdown com ${availableServices.size} serviços")
                                    availableServices.filter { service ->
                                        !service.name.isNullOrBlank() && service.price >= 0
                                    }.forEach { service ->
                                        Log.d("CreateScheduleDialog", "🔍 Renderizando serviço: ${service.name ?: "null"}")
                                        val isSelected = selectedServices.any { 
                                            it.id == service.id && it.name == service.name 
                                        }
                                        DropdownMenuItem(
                                            text = {
                                                Row(
                                                    modifier = Modifier.fillMaxWidth(),
                                                    horizontalArrangement = Arrangement.SpaceBetween
                                                ) {
                                                    Text(service.name ?: "Serviço sem nome")
                                                    Text("R$ ${service.price}", fontSize = 12.sp)
                                                }
                                            },
                                            onClick = {
                                                Log.d("CreateScheduleDialog", "🔍 Clicou no serviço: ${service.name ?: "null"}")
                                                selectedServices = if (isSelected) {
                                                    selectedServices.filterNot { 
                                                        it.id == service.id && it.name == service.name 
                                                    }
                                                } else {
                                                    selectedServices + service
                                                }
                                                Log.d("CreateScheduleDialog", "✅ Serviços selecionados: ${selectedServices.size}")
                                            },
                                            trailingIcon = {
                                                if (isSelected) {
                                                    Icon(
                                                        Icons.Default.Schedule,
                                                        contentDescription = null,
                                                        tint = customColorScheme.primary
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            }

                            // Mostrar serviços selecionados
                            if (selectedServices.isNotEmpty()) {
                                Spacer(modifier = Modifier.height(8.dp))
                                selectedServices.forEach { service ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 2.dp),
                                        colors = CardDefaults.cardColors(containerColor = customColorScheme.primary.copy(alpha = 0.1f))
                                    ) {
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(8.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text(service.name ?: "Serviço sem nome", fontSize = 12.sp)
                                            Text("R$ ${service.price}", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Dropdown Funcionário
                            Text(
                                text = "Escolha um profissional",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Box {
                                OutlinedCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { 
                                            Log.d("CreateScheduleDialog", "🔍 Clicou no dropdown de funcionários. Disponíveis: ${availableEmployees.size}")
                                            if (availableEmployees.isNotEmpty()) {
                                                showEmployeeDropdown = !showEmployeeDropdown 
                                                Log.d("CreateScheduleDialog", "📂 Dropdown funcionários aberto: $showEmployeeDropdown")
                                            } else {
                                                Log.w("CreateScheduleDialog", "⚠️ Não há funcionários para mostrar!")
                                            }
                                        },
                                    colors = CardDefaults.outlinedCardColors(containerColor = Color.White)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = selectedEmployee?.name ?: if (availableEmployees.isEmpty()) "Nenhum funcionário disponível" else "Selecione o profissional",
                                            color = if (selectedEmployee != null) Color.Black else Color.Gray
                                        )
                                        if (availableEmployees.isNotEmpty()) {
                                            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                                        }
                                    }
                                }

                                DropdownMenu(
                                    expanded = showEmployeeDropdown,
                                    onDismissRequest = { showEmployeeDropdown = false }
                                ) {
                                    availableEmployees.forEach { employee ->
                                        DropdownMenuItem(
                                            text = { Text(employee.name) },
                                            onClick = {
                                                Log.d("CreateScheduleDialog", "👤 Selecionou funcionário: ID=${employee.id}, Nome='${employee.name}'")
                                                selectedEmployee = employee
                                                showEmployeeDropdown = false
                                                Log.d("CreateScheduleDialog", "✅ Funcionário selecionado salvo: ${selectedEmployee?.name}")
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Data e Horário
                            Text(
                                text = "Quando deseja agendar?*",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            DateInputField(
                                value = selectedDate,
                                onValueChange = { selectedDate = it },
                                label = "Data (dd/mm/yyyy)",
                                isError = isFormSubmitted && selectedDate.isEmpty()
                            )

                            Spacer(modifier = Modifier.height(12.dp))

                            TimeInputField(
                                value = selectedTime,
                                onValueChange = { selectedTime = it },
                                label = "Horário (HH:mm)",
                                isError = isFormSubmitted && selectedTime.isEmpty()
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            // Dropdown Forma de Pagamento
                            Text(
                                text = "Como deseja pagar?*",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            Box {
                                OutlinedCard(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable { showPaymentDropdown = !showPaymentDropdown },
                                    colors = CardDefaults.outlinedCardColors(
                                        containerColor = if (isFormSubmitted && selectedPaymentMethod == null)
                                            customColorScheme.error.copy(alpha = 0.1f)
                                        else Color.White
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(12.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = selectedPaymentMethod?.displayName ?: "Selecione a forma de pagamento",
                                            color = if (selectedPaymentMethod != null) Color.Black else Color.Gray
                                        )
                                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                                    }
                                }

                                DropdownMenu(
                                    expanded = showPaymentDropdown,
                                    onDismissRequest = { showPaymentDropdown = false }
                                ) {
                                    paymentMethods.forEach { paymentMethod ->
                                        DropdownMenuItem(
                                            text = { Text(paymentMethod.displayName) },
                                            onClick = {
                                                selectedPaymentMethod = paymentMethod
                                                showPaymentDropdown = false
                                            },
                                            leadingIcon = {
                                                Icon(
                                                    Icons.Default.Payment,
                                                    contentDescription = null,
                                                    tint = customColorScheme.primary
                                                )
                                            }
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Observações
                            Text(
                                text = "Deixe um recado para a equipe",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )

                            OutlinedTextField(
                                value = notes,
                                onValueChange = { notes = it },
                                label = { Text("Observações (opcional)") },
                                placeholder = { Text("Observações (opcional)") },
                                modifier = Modifier.fillMaxWidth(),
                                minLines = 3
                            )

                            Spacer(modifier = Modifier.height(20.dp))

                            // Total do Pagamento
                            if (totalPrice > 0) {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = customColorScheme.primary.copy(alpha = 0.1f))
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(16.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "Total do Pagamento:",
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(
                                            text = "R$ $totalPrice",
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = customColorScheme.primary
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(20.dp))
                            }
                        }
                    }

                    // Botões fixos na parte inferior (apenas se não está carregando)
                    if (!viewModel.isLoadingData) {
                        Column {
                            // Botão Confirmar
                            Button(
                                onClick = {
                                    Log.d("CreateScheduleDialog", "🔘 Botão Confirmar clicado!")
                                    Log.d("CreateScheduleDialog", "📝 Validação do formulário:")
                                    Log.d("CreateScheduleDialog", "Pet selecionado: ${selectedPet?.name ?: "null"}")
                                    Log.d("CreateScheduleDialog", "Serviços selecionados: ${selectedServices.size}")
                                    Log.d("CreateScheduleDialog", "Funcionário selecionado: ${selectedEmployee?.name ?: "NENHUM"} (ID: ${selectedEmployee?.id ?: "NULL"})")
                                    Log.d("CreateScheduleDialog", "Data: '$selectedDate' - Válida: ${DateTimeUtils.validateDate(selectedDate)}")
                                    Log.d("CreateScheduleDialog", "Hora: '$selectedTime' - Válida: ${DateTimeUtils.validateTime(selectedTime)}")
                                    Log.d("CreateScheduleDialog", "Método pagamento: ${selectedPaymentMethod?.displayName ?: "null"}")
                                    Log.d("CreateScheduleDialog", "Formulário válido: ${validateForm()}")
                                    
                                    if (validateForm()) {
                                        // Converter para os tipos esperados pelo ScheduleFormData
                                        val petResumo = PetResumo(
                                            id = selectedPet!!.id,
                                            name = selectedPet!!.name
                                        )
                                        
                                        val servicesResumo = selectedServices.mapNotNull { service ->
                                            Log.d("CreateScheduleDialog", "🔄 Convertendo serviço: ID=${service.id}, Nome=${service.name ?: "null"}, Preço=${service.price}")
                                            service.name?.let { name ->
                                                ServiceResumo(
                                                    id = service.id ?: 0,
                                                    name = name,
                                                    price = service.price
                                                )
                                            }
                                        }
                                        
                                        val employeeResumo = selectedEmployee?.let { emp ->
                                            Log.d("CreateScheduleDialog", "🔄 Convertendo funcionário: ID=${emp.id}, Nome=${emp.name}")
                                            EmployeeResumo(
                                                id = emp.id,
                                                name = emp.name
                                            )
                                        }
                                        
                                        Log.d("CreateScheduleDialog", "👤 EmployeeResumo final: ${if (employeeResumo == null) "NULL" else "ID=${employeeResumo.id}, Nome=${employeeResumo.name}"}")

                                        val scheduleFormData = ScheduleFormData(
                                            pet = petResumo,
                                            services = servicesResumo,
                                            employee = employeeResumo,
                                            date = selectedDate,
                                            time = selectedTime,
                                            paymentMethod = selectedPaymentMethod!!,
                                            notes = notes,
                                            totalPrice = totalPrice
                                        )
                                        
                                        Log.d("CreateScheduleDialog", "✅ Formulário válido! Chamando onConfirm com dados:")
                                        Log.d("CreateScheduleDialog", "Pet: ${scheduleFormData.pet.name}")
                                        Log.d("CreateScheduleDialog", "Serviços: ${scheduleFormData.services.size}")
                                        Log.d("CreateScheduleDialog", "Funcionário: ${scheduleFormData.employee?.name ?: "NENHUM"} (ID: ${scheduleFormData.employee?.id ?: "NULL"})")
                                        Log.d("CreateScheduleDialog", "Data/Hora: ${scheduleFormData.date} ${scheduleFormData.time}")
                                        Log.d("CreateScheduleDialog", "Pagamento: ${scheduleFormData.paymentMethod.displayName}")
                                        Log.d("CreateScheduleDialog", "Total: R$ ${scheduleFormData.totalPrice}")
                                        
                                        onConfirm(scheduleFormData)
                                        setOpenCreateScheduleDialog(false)
                                    } else {
                                        isFormSubmitted = true
                                    }
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isFormSubmitted && !validateForm()) customColorScheme.error else customColorScheme.primary,
                                    contentColor = Color.White,
                                ),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Confirmar",
                                        modifier = Modifier.weight(1f),
                                        textAlign = TextAlign.Center,
                                        style = buttonTextStyle
                                    )
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        contentDescription = "Seta para a direita"
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Botão Cancelar
                            Button(
                                onClick = {
                                    setOpenCreateScheduleDialog(false)
                                },
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = customColorScheme.primary,
                                    containerColor = customColorScheme.secondary,
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp)
                            ) {
                                Text(
                                    text = "Cancelar",
                                    modifier = Modifier.weight(1f),
                                    textAlign = TextAlign.Center,
                                    style = buttonTextStyle
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Data classes para o formulário
data class ScheduleFormData(
    val pet: PetResumo,
    val services: List<ServiceResumo>,
    val employee: EmployeeResumo?,
    val date: String,
    val time: String,
    val paymentMethod: PaymentMethod,
    val notes: String,
    val totalPrice: Double
)

data class PetResumo(
    val id: Int,
    val name: String
)

data class ServiceResumo(
    val id: Int,
    val name: String,
    val price: Double
)

data class EmployeeResumo(
    val id: Int,
    val name: String
)

data class PaymentMethod(
    val id: String,
    val displayName: String
)