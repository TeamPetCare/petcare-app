package com.example.petcare_app.ui.components.dialogs.createSchedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.components.formFields.CustomDropdown
import com.example.petcare_app.ui.theme.buttonTextStyle
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreateScheduleDialog(
    setOpenCreateScheduleDialog: (Boolean) -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    var inputDate by remember { mutableStateOf("") }

    var petSelecionado by remember { mutableStateOf("") }

    AlertDialog(
        containerColor = Color.White,
        onDismissRequest = { setOpenCreateScheduleDialog(false) },
        title = {
            Text(
                text = "Agendar Atendimento",
                style = sentenceTitleTextStyle,
                color = customColorScheme.primary,
            )
        },
        text = {
            Column {
//                CustomDropdown(
//                    value = petSelecionado,
//                    onValueChange = { novoPet ->
//                        petSelecionado = novoPet
//                    },
//                    label = "",
//                    placeholder = "Qual pet será atendido?*",
//                    options = listOf("Pet 1", "Pet 2", "Pet 3"),
//                    modifier = Modifier.fillMaxWidth(),
//                    isFormSubmitted = isFormSubmitted,
//                    isError = petErro,
//                    isRequired = true
//                )
                TextField(
                    value = inputDate,
                    onValueChange = { inputDate = it },
                    label = { Text("Data (ex: 2024-05-01 14:00)") }
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
//                    if (validateForm()) {
//                        coroutineScope.launch {
//                            delay(150)
//                            navController.navigate(Screen.SignUpPet.route)
//                        }
//                    } else isFormSubmitted = true
                    // Aqui você pega os valores e cria o agendamento
                    // Por exemplo, pode chamar sua ViewModel ou mandar pra API
                    setOpenCreateScheduleDialog(false)
                },
                colors = buttonColors(
//                    containerColor = if (isFormSubmitted) customColorScheme.error else customColorScheme.primary,
                    contentColor = Color.White,
                    containerColor = customColorScheme.primary,
                )
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
        },
        dismissButton = {
            Button(
                onClick = {
//                    if (validateForm()) {
//                        coroutineScope.launch {
//                            delay(150)
//                            navController.navigate(Screen.SignUpPet.route)
//                        }
//                    } else isFormSubmitted = true
                    // Aqui você pega os valores e cria o agendamento
                    // Por exemplo, pode chamar sua ViewModel ou mandar pra API
                    setOpenCreateScheduleDialog(false)
                },
                colors = buttonColors(
//                    containerColor = if (isFormSubmitted) customColorScheme.error else customColorScheme.primary,
                    contentColor = customColorScheme.primary,
                    containerColor = customColorScheme.secondary,
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Cancelar",
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = buttonTextStyle
                )
            }
        }
    )
}
