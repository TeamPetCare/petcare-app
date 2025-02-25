package com.example.petcare_app.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun CustomTextInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier,
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,  // Cor de fundo quando focado
            unfocusedContainerColor = Color.Transparent, // Cor de fundo quando não focado
            focusedTextColor = MaterialTheme.colorScheme.surface,  // Cor do texto focado
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface, // Cor do texto não focado
            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface // Cor da borda quando não focado
        )
    )
}
