package com.example.petcare_app.ui.components.formFields

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.errorTextStyle
import com.example.petcare_app.ui.theme.montserratFontFamily

@Composable
fun CustomRadioBox(
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    label: String,
    options: List<String>,
    modifier: Modifier = Modifier,
    isFormSubmitted: Boolean,
    isError: Boolean = false,
    isRequired: Boolean
) {
    var isValid by remember { mutableStateOf(true) }
    val labelText = if (isRequired) "$label*" else label

    LaunchedEffect(isFormSubmitted) {
        if (isFormSubmitted && isRequired) isValid = selectedOption.isNotEmpty()
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            modifier = Modifier
                .padding(bottom = 4.dp),
            text = labelText,
            fontFamily = montserratFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = customColorScheme.surface
        )

        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 7.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedOption == option,
                    modifier = Modifier.size(15.dp),
                    onClick = { onOptionSelected(option) },
                    colors = RadioButtonDefaults.colors(
                        selectedColor = customColorScheme.onSurface,
                        unselectedColor = customColorScheme.onSurface
                    )
                )
                Text(
                    text = option,
                    fontSize = 14.sp,
                    fontFamily = montserratFontFamily,
                    color = customColorScheme.surface,
                    modifier = Modifier.padding(start = 13.dp)
                )
            }
        }

        if (isError) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = when (label) {
                    "Porte" -> "*Selecione um porte."
                    "Sexo" -> "*Selecione um sexo."
                    else -> ""
                },
                style = errorTextStyle
            )
        }
    }
}
