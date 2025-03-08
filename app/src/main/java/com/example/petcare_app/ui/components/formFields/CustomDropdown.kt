package com.example.petcare_app.ui.components.formFields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.errorTextStyle
import com.example.petcare_app.ui.theme.innerInputTextStyle
import com.example.petcare_app.ui.theme.montserratFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdown(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    options: List<String>,
    modifier: Modifier = Modifier,
    isFormSubmitted: Boolean,
    isError: Boolean = false,
    isRequired: Boolean
) {
    var expanded by remember { mutableStateOf(false) }
    val labelText = if (isRequired) "$label*" else label

    var isValid by remember { mutableStateOf(true) }

    LaunchedEffect(isFormSubmitted) {
        if (isFormSubmitted && isRequired) isValid = value.isNotEmpty() && value != placeholder
    }

    Column(modifier = Modifier) {
        Text(
            modifier = Modifier
                .padding(bottom = 1.dp),
            text = labelText,
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = customColorScheme.surface
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                textStyle = innerInputTextStyle,
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text("$placeholder", style = innerInputTextStyle) },
                modifier = modifier.fillMaxWidth(),
                singleLine = true,
                isError = isError,
                readOnly = true, // Não permite edição direta, apenas a seleção do dropdown
                shape = MaterialTheme.shapes.small,
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = customColorScheme.onSurface,
                    unfocusedIndicatorColor = customColorScheme.onSurface,
                    focusedIndicatorColor = customColorScheme.onSurface,
                    errorContainerColor = Color.Transparent,
                ),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(option)
                            expanded = false
                        },
                        text = { Text(text = option) }
                    )
                }
            }
        }

        if (isError) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = when (label) {
                    "Espécie" -> "*Selecione uma espécie."
                    else -> ""
                },
                style = errorTextStyle
            )
        }
    }
}

