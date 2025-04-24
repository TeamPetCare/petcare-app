package com.example.petcare_app.ui.components.formFields.inputFields

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

class DateVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val trimmed = text.text.take(8).filter { it.isDigit() }
        val formatted = buildString {
            for (i in trimmed.indices) {
                append(trimmed[i])
                if (i == 1 || i == 3) append("/")
            }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                return when {
                    offset <= 1 -> offset
                    offset <= 3 -> offset + 1
                    offset <= 8 -> offset + 2
                    else -> formatted.length
                }
            }

            override fun transformedToOriginal(offset: Int): Int {
                return when {
                    offset <= 2 -> offset
                    offset <= 5 -> offset - 1
                    offset <= 10 -> offset - 2
                    else -> text.text.length
                }
            }
        }

        return TransformedText(AnnotatedString(formatted), offsetMapping)
    }
}

fun isValidDateNotPast(date: String): Boolean {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        sdf.isLenient = false
        val parsedDate = sdf.parse(date)
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        parsedDate != null && !parsedDate.after(today.time)
    } catch (e: Exception) {
        false
    }
}

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

    val digitsOnly = value.filter { it.isDigit() }.take(8)
    var textFieldValue by remember {
        mutableStateOf(
            TextFieldValue(
                text = digitsOnly,
                selection = TextRange(digitsOnly.length)
            )
        )
    }

    LaunchedEffect(isFormSubmitted) {
        if (isFormSubmitted && isRequired) {
            isValid = value.isNotEmpty() && isValidDateNotPast(value)
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier.padding(bottom = 1.dp),
            text = labelText,
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = customColorScheme.surface
        )

        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newValue ->
                val digits = newValue.text.filter { it.isDigit() }.take(8)
                val updatedTextFieldValue = TextFieldValue(
                    text = digits,
                    selection = TextRange(digits.length)
                )
                textFieldValue = updatedTextFieldValue

                val formattedDate = buildString {
                    for (i in digits.indices) {
                        append(digits[i])
                        if (i == 1 || i == 3) append("/")
                    }
                }

                onValueChange(formattedDate)
                isValid = formattedDate.length < 10 || isValidDateNotPast(formattedDate)
            },
            placeholder = { Text(placeholder) },
            modifier = modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isError || !isValid,
            singleLine = true,
            shape = MaterialTheme.shapes.small,
            visualTransformation = DateVisualTransformation(),
            textStyle = innerInputTextStyle,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = customColorScheme.onSurface,
                unfocusedIndicatorColor = customColorScheme.onSurface,
                focusedIndicatorColor = customColorScheme.onSurface,
                errorContainerColor = Color.Transparent,
            ),
        )

        if (isError || !isValid) {
            Text(
                text = msgErro.ifEmpty { "Data inválida ou anterior à data atual" },
                style = errorTextStyle,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
