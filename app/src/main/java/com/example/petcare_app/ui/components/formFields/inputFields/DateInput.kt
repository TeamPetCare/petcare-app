package com.example.petcare_app.ui.components.formFields.inputFields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.errorTextStyle
import com.example.petcare_app.ui.theme.innerInputTextStyle
import com.example.petcare_app.ui.theme.montserratFontFamily
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun DateInput(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    isFormSubmitted: Boolean,
    isError: Boolean = false,
    msgErro: String = "",
    isRequired: Boolean
) {
    var isValid by remember { mutableStateOf(true) }
    val labelText = if (isRequired) "$label*" else label

    LaunchedEffect(isFormSubmitted) {
        if (isFormSubmitted && isRequired) {
            isValid = value.isNotEmpty()
        }
    }

    fun isValidDate(date: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }

    Column(modifier = Modifier) {
        Text(
            modifier = Modifier.padding(bottom = 1.dp),
            text = labelText,
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = customColorScheme.surface
        )

        var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }

        OutlinedTextField(
            textStyle = innerInputTextStyle,
            value = textFieldValue,
            onValueChange = { newValue ->
                val unformattedText = newValue.text.filter { it.isDigit() }.take(8)

                val formattedText = buildString {
                    for (i in unformattedText.indices) {
                        append(unformattedText[i])
                        if (i == 1 || i == 3) append("/")
                    }
                }

                val newCursorPosition = when {
                    newValue.selection.start <= 2 -> newValue.selection.start
                    newValue.selection.start <= 5 -> newValue.selection.start + 1
                    else -> newValue.selection.start + 2
                }.coerceAtMost(formattedText.length)

                textFieldValue = TextFieldValue(
                    text = formattedText,
                    selection = TextRange(newCursorPosition)
                )

                onValueChange(formattedText)
                isValid = formattedText.length < 10 || isValidDate(formattedText)
            },
            placeholder = { Text(placeholder) },
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isError,
            singleLine = true,
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = customColorScheme.onSurface,
                unfocusedIndicatorColor = customColorScheme.onSurface,
                focusedIndicatorColor = customColorScheme.onSurface,
                errorContainerColor = Color.Transparent,
            ),
        )
    }

    if (isError) {
        Text(
            text = msgErro,
            color = MaterialTheme.colorScheme.error,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 4.dp)
        )
    }
}
