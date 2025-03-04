package com.example.petcare_app.ui.components.inputFields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.R
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.errorTextStyle
import com.example.petcare_app.ui.theme.innerInputTextStyle
import com.example.petcare_app.ui.theme.montserratFontFamily


@Composable
fun PasswordInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isFormSubmitted: Boolean,
    isError: Boolean,
    confirmarSenha: Boolean
) {
    val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$".toRegex()
    var isValid by remember { mutableStateOf(true) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val eyeOpen: Painter = painterResource(id = R.drawable.ic_eye)
    val eyeClosed: Painter = painterResource(id = R.drawable.ic_eye_closed)

    LaunchedEffect(isFormSubmitted) {
        if (isFormSubmitted) {
            isValid = value.isEmpty() || value.matches(passwordPattern)
        }
    }

    Column(modifier = Modifier) {
        Text(
            modifier = Modifier
                .padding(bottom = 1.dp),
            text = "$label*",
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
            singleLine = true,
            isError = isError,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = customColorScheme.onSurface,
                unfocusedIndicatorColor = customColorScheme.onSurface,
                focusedIndicatorColor = customColorScheme.onSurface,
                errorContainerColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        painter = if (isPasswordVisible) eyeClosed else eyeOpen,
                        contentDescription = if (isPasswordVisible) "Ocultar Senha" else "Exibir Senha"
                    )
                }
            }
        )

        if (isError) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = if (confirmarSenha) "*As senhas não coincidem. Tente novamente." else "*Insire uma senha válida. Mínimo de 8 caracteres, incluindo maiúsculas, minúsculas, números e caracteres especiais.",
                style = errorTextStyle
            )
        }
    }
}
