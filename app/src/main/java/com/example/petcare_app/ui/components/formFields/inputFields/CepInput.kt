package com.example.petcare_app.ui.components.formFields.inputFields

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.errorTextStyle
import com.example.petcare_app.ui.theme.innerInputTextStyle
import com.example.petcare_app.ui.theme.montserratFontFamily
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

@Composable
fun CepInput(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isFormSubmitted: Boolean,
    isError: Boolean,
    onAddressRetrieved: (String, String, String) -> Unit,
    addressRetrieved: String
) {
    var isValid by remember { mutableStateOf(true) }
    var loading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var previousValue by remember { mutableStateOf("") }

    var addressData by remember { mutableStateOf<AddressData?>(null) }

    fun maskCep(input: String): String {
        return input.replace(Regex("(\\d{5})(\\d{3})"), "$1-$2")
    }

    val maskedValue = maskCep(value)

    LaunchedEffect(value) {
        if (value.isNotEmpty() && addressRetrieved.isNullOrEmpty()) {
            if (value != previousValue && value.length == 8) {
                previousValue = value
                loading = true
                val address = fetchCepData(value)
                loading = false
                if (address != null) {
                    addressData = address
                    onAddressRetrieved(address.logradouro, address.bairro, address.cidade)
                    errorMessage = null
                    isValid = true
                } else {
                    errorMessage = "CEP inválido ou não encontrado"
                    isValid = false
                }
            }
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
            value = maskedValue,
            onValueChange = {
                val numericValue = it.filter { char -> char.isDigit() }
                if (numericValue.length <= 8) {
                    onValueChange(numericValue)
                }
            },
            placeholder = { Text("$placeholder", style = innerInputTextStyle) },
            modifier = modifier,
            singleLine = true,
            isError =  isError || !isValid,
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
                keyboardType = KeyboardType.Number
            ),
            trailingIcon = {
                if (loading) {
                    CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                }
            }
        )

        if (isError || !isValid) {
            Text(
                modifier = Modifier
                    .padding(top = 4.dp),
                text = errorMessage ?: "*Insira um CEP válido.",
                style = errorTextStyle
            )
        }
    }
}

suspend fun fetchCepData(cep: String): AddressData? {
    return withContext(Dispatchers.IO) {
        try {
            val response = URL("https://viacep.com.br/ws/$cep/json/").readText()
            val jsonObject = JSONObject(response)

            if (!jsonObject.has("erro")) {
                val address = AddressData(
                    jsonObject.getString("logradouro"),
                    jsonObject.getString("bairro"),
                    jsonObject.getString("localidade"),
                )
                address
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }
}

data class AddressData(val logradouro: String, val bairro: String, val cidade: String)
