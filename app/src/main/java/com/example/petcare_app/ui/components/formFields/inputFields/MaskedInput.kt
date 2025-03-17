package com.example.petcare_app.ui.components.formFields.inputFields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.errorTextStyle
import com.example.petcare_app.ui.theme.innerInputTextStyle
import com.example.petcare_app.ui.theme.montserratFontFamily
import java.lang.Error

// Função para validar o CPF
fun isValidCPF(cpf: String): Boolean {
    var cpf = cpf.replace("[^0-9]".toRegex(), "")

    if (cpf.length != 11 || cpf.all { it == cpf[0] }) {
        return false
    }

    val digits = cpf.map { it.toString().toInt() }

    val firstCheckDigit = calculateDigit(digits, 10)
    val secondCheckDigit = calculateDigit(digits, 11)

    return firstCheckDigit == digits[9] && secondCheckDigit == digits[10]
}

// Função auxiliar para calcular os dígitos verificadores
fun calculateDigit(digits: List<Int>, multiplier: Int): Int {
    var sum = 0
    var factor = multiplier

    for (i in 0 until multiplier - 1) {
        sum += digits[i] * factor
        factor--
    }

    val remainder = sum % 11
    return if (remainder < 2) 0 else 11 - remainder
}

@Composable
fun MaskedInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    type: String,
    modifier: Modifier = Modifier,
    isFormSubmitted: Boolean,
    isError: Boolean
) {
    var isValid by remember { mutableStateOf(true) }

    fun maskCpf(input: String): String {
        return input.replace(Regex("(\\d{3})(\\d{3})(\\d{3})(\\d{2})"), "$1.$2.$3-$4")
    }

    fun maskCelular(input: String): String {
        return input.replace(Regex("(\\d{2})(\\d{5})(\\d{4})"), "($1) $2-$3")
    }

    val maskedValue = when (type) {
        "CPF" -> maskCpf(value)
        "Celular" -> maskCelular(value)
        else -> value
    }

    LaunchedEffect(isFormSubmitted) {
        if (type == "CPF") {
            val unformattedCpf = value.replace("[^0-9]".toRegex(), "")
            isValid = unformattedCpf.isNotEmpty() && isValidCPF(unformattedCpf)
        } else if (type == "Celular") {
            isValid = value.isEmpty() || value.matches("^\\(\\d{2}\\) \\d{5}-\\d{4}$".toRegex())
        }
    }

    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(bottom = 1.dp),
            text = "$label*",
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = customColorScheme.surface
        )

        OutlinedTextField(
            textStyle = innerInputTextStyle,
            value = maskedValue,
            onValueChange = { newText ->
                val unformattedText = newText.replace(Regex("[^0-9]"), "")
                onValueChange(unformattedText)
            },
            placeholder = { Text("$placeholder", style = innerInputTextStyle) },
            modifier = modifier,
            singleLine = true,
            isError = isError,
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = customColorScheme.onSurface,
                unfocusedIndicatorColor = customColorScheme.onSurface,
                focusedIndicatorColor = customColorScheme.onSurface,
                errorContainerColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        if (isError) {
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = when (type) {
                    "CPF" -> "*Insira um CPF válido. Apenas números."
                    "Celular" -> "*Insira um número de telefone válido. Apenas números."
                    else -> ""
                },
                style = errorTextStyle
            )
        }
    }
}
