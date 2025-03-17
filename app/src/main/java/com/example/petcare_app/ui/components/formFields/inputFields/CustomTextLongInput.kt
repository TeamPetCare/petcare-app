package com.example.petcare_app.ui.components.formFields.inputFields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.errorTextStyle
import com.example.petcare_app.ui.theme.innerInputTextStyle
import com.example.petcare_app.ui.theme.montserratFontFamily

@Composable
fun CustomTextLongInput(
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
            isValid = when (label) {
                "Nome" -> value.length >= 2
                else -> value.length >= 4
            }
        }
    }

    Column (modifier = Modifier) {
        Text(
            modifier = Modifier
                .padding(bottom = 1.dp),
            text = labelText,
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = customColorScheme.surface
        )

        OutlinedTextField(
            textStyle = innerInputTextStyle,
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text("$placeholder", style = innerInputTextStyle) },
            modifier = modifier,
            singleLine = false,
            isError = isError,
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

        if (isError) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = msgErro,
                style = errorTextStyle
            )
        }

    }

}
