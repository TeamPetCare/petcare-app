package com.example.petcare_app.ui.components.scheduleComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.data.viewmodel.PetResumo
import com.example.petcare_app.ui.theme.customColorScheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleMenuComponent(
    pets: List<PetResumo>,
    services: List<ServiceResumo>,
    employees: List<EmployeeResumo>,
    onConfirm: (ScheduleFormData) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedPet by remember { mutableStateOf<PetResumo?>(null) }
    var selectedServices by remember { mutableStateOf<List<ServiceResumo>>(emptyList()) }
    var selectedEmployee by remember { mutableStateOf<EmployeeResumo?>(null) }
    var selectedDate by remember { mutableStateOf("") }
    var selectedTime by remember { mutableStateOf("") }
    var notes by remember { mutableStateOf("") }
    var totalPrice by remember { mutableStateOf(0.0) }

    var showPetDropdown by remember { mutableStateOf(false) }
    var showServiceDropdown by remember { mutableStateOf(false) }
    var showEmployeeDropdown by remember { mutableStateOf(false) }

    // Calcular preço total quando serviços mudam,
    LaunchedEffect(selectedServices) {
        totalPrice = selectedServices.sumOf { it.price }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                text = "Agendar Atendimento",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = customColorScheme.primary,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Dropdown Pet
            Text(
                text = "Qual pet será atendido?",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Box {
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showPetDropdown = !showPetDropdown },
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
                            text = selectedPet?.name ?: "Selecione o pet",
                            color = if (selectedPet != null) Color.Black else Color.Gray
                        )
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }

                DropdownMenu(
                    expanded = showPetDropdown,
                    onDismissRequest = { showPetDropdown = false }
                ) {
                    pets.forEach { pet ->
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
                text = "Escolha o(s) serviço(s) desejado(s)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Box {
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { showServiceDropdown = !showServiceDropdown },
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
                            text = if (selectedServices.isNotEmpty())
                                "${selectedServices.size} serviço(s) selecionado(s)"
                            else "Selecione os serviços",
                            color = if (selectedServices.isNotEmpty()) Color.Black else Color.Gray
                        )
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }

                DropdownMenu(
                    expanded = showServiceDropdown,
                    onDismissRequest = { showServiceDropdown = false }
                ) {
                    services.forEach { service ->
                        val isSelected = selectedServices.contains(service)
                        DropdownMenuItem(
                            text = {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(service.name)
                                    Text("R$ ${service.price}", fontSize = 12.sp)
                                }
                            },
                            onClick = {
                                selectedServices = if (isSelected) {
                                    selectedServices - service
                                } else {
                                    selectedServices + service
                                }
                            },
                            trailingIcon = {
                                if (isSelected) {
                                    Icon(
                                        Icons.Default.Schedule, // Usando ícone disponível como check
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
                            Text(service.name, fontSize = 12.sp)
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
                        .clickable { showEmployeeDropdown = !showEmployeeDropdown },
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
                            text = selectedEmployee?.name ?: "Selecione o profissional",
                            color = if (selectedEmployee != null) Color.Black else Color.Gray
                        )
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }

                DropdownMenu(
                    expanded = showEmployeeDropdown,
                    onDismissRequest = { showEmployeeDropdown = false }
                ) {
                    employees.forEach { employee ->
                        DropdownMenuItem(
                            text = { Text(employee.name) },
                            onClick = {
                                selectedEmployee = employee
                                showEmployeeDropdown = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Data
            Text(
                text = "Quando deseja agendar?",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            OutlinedTextField(
                value = selectedDate,
                onValueChange = { selectedDate = it },
                label = { Text("Data (dd/mm/yyyy)") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null) }
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = selectedTime,
                onValueChange = { selectedTime = it },
                label = { Text("Horário (HH:mm)") },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = { Icon(Icons.Default.Schedule, contentDescription = null) }
            )

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

            // Botão Confirmar
            Button(
                onClick = {
                    if (selectedPet != null && selectedServices.isNotEmpty() &&
                        selectedDate.isNotEmpty() && selectedTime.isNotEmpty()) {
                        onConfirm(
                            ScheduleFormData(
                                pet = selectedPet!!,
                                services = selectedServices,
                                employee = selectedEmployee,
                                date = selectedDate,
                                time = selectedTime,
                                notes = notes,
                                totalPrice = totalPrice
                            )
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = customColorScheme.primary),
                enabled = selectedPet != null && selectedServices.isNotEmpty() &&
                        selectedDate.isNotEmpty() && selectedTime.isNotEmpty()
            ) {
                Text(
                    text = "Confirmar",
                    color = Color.White,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Botão Cancelar
            Spacer(modifier = Modifier.height(8.dp))

            TextButton(
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Cancelar",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
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
    val notes: String,
    val totalPrice: Double
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