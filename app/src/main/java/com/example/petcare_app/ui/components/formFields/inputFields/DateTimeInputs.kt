package com.example.petcare_app.ui.components.formFields.inputFields

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.AnnotatedString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Data (dd/mm/yyyy)",
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            val cleaned = newValue.filter { it.isDigit() }
            val masked = when {
                cleaned.length <= 2 -> cleaned
                cleaned.length <= 4 -> "${cleaned.substring(0, 2)}/${cleaned.substring(2)}"
                cleaned.length <= 8 -> "${cleaned.substring(0, 2)}/${cleaned.substring(2, 4)}/${cleaned.substring(4)}"
                else -> "${cleaned.substring(0, 2)}/${cleaned.substring(2, 4)}/${cleaned.substring(4, 8)}"
            }
            if (masked.length <= 10) {
                onValueChange(masked)
            }
        },
        label = { Text(label) },
        placeholder = { Text("dd/mm/yyyy") },
        leadingIcon = { 
            Icon(Icons.Default.CalendarToday, contentDescription = null) 
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = isError,
        modifier = modifier.fillMaxWidth(),
        singleLine = true
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String = "Horário (HH:mm)",
    isError: Boolean = false,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            val cleaned = newValue.filter { it.isDigit() }
            val masked = when {
                cleaned.length <= 2 -> cleaned
                cleaned.length <= 4 -> "${cleaned.substring(0, 2)}:${cleaned.substring(2)}"
                else -> "${cleaned.substring(0, 2)}:${cleaned.substring(2, 4)}"
            }
            // Validar hora (00-23) e minutos (00-59)
            val parts = masked.split(":")
            var isValid = true
            if (parts.size >= 2) {
                val hour = parts[0].toIntOrNull() ?: 0
                val minute = parts[1].toIntOrNull() ?: 0
                if (hour > 23 || minute > 59) {
                    isValid = false
                }
            }
            if (parts.size == 1 && parts[0].length == 2) {
                val hour = parts[0].toIntOrNull() ?: 0
                if (hour > 23) {
                    isValid = false
                }
            }
            
            if (isValid && masked.length <= 5) {
                onValueChange(masked)
            }
        },
        label = { Text(label) },
        placeholder = { Text("HH:mm") },
        leadingIcon = { 
            Icon(Icons.Default.Schedule, contentDescription = null) 
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        isError = isError,
        modifier = modifier.fillMaxWidth(),
        singleLine = true
    )
} 