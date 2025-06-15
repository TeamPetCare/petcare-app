package com.example.petcare_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable
import com.example.petcare_app.ui.components.layouts.WhiteCanvas
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.buttonTextStyle
import com.example.petcare_app.ui.theme.innerInputTextStyle
import com.example.petcare_app.ui.theme.montserratFontFamily

// Transformação visual para data
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetRegisterScreen(navController: NavController) {
    // Estados dos campos
    var nome by remember { mutableStateOf("") }
    var especie by remember { mutableStateOf("") }
    var raca by remember { mutableStateOf("") }
    var dataNascimento by remember { mutableStateOf("") }
    var cor by remember { mutableStateOf("") }
    var porte by remember { mutableStateOf("") }
    var sexo by remember { mutableStateOf("") }
    var observacoes by remember { mutableStateOf("") }

    // Estados para dropdowns
    var especieExpanded by remember { mutableStateOf(false) }
    var racaExpanded by remember { mutableStateOf(false) }

    // Estado para campo de data
    var textFieldValue by remember {
        mutableStateOf(TextFieldValue(text = "", selection = TextRange(0)))
    }

    // Opções para os dropdowns
    val especies = listOf("Cachorro", "Gato", "Outro")
    val racas = listOf("SRD", "Poodle", "Persa", "Outro")
    val portes = listOf("Pequeno", "Médio", "Grande")
    val sexos = listOf("Masculino", "Feminino")

    // Cor personalizada para as bordas dos campos
    val borderColor = Color(0xFFD8DADC)

    Scaffold(
        topBar = { HeaderComposable(navController) },
        bottomBar = { GadjetBarComposable(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(Color(0, 84, 114))
                .padding(paddingValues)
        ) {
            WhiteCanvas(
                modifier = Modifier.fillMaxHeight(),
                icon = Icons.Filled.Pets,
                title = "Pets Cadastrados",
                visibleBackButton = true,
                navController = navController
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Cadastrar novo Pet",
                        fontFamily = montserratFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = customColorScheme.primary
                    )

                    // Nome
                    Column {
                        Text(
                            text = "Nome*",
                            fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = customColorScheme.surface,
                            modifier = Modifier.padding(bottom = 1.dp)
                        )
                        OutlinedTextField(
                            value = nome,
                            onValueChange = { nome = it },
                            placeholder = {
                                Text(
                                    "Digite o nome do pet",
                                    style = innerInputTextStyle
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            singleLine = true,
                            shape = MaterialTheme.shapes.small,
                            textStyle = innerInputTextStyle,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedTextColor = customColorScheme.onSurface,
                                unfocusedIndicatorColor = borderColor,
                                focusedIndicatorColor = borderColor,
                                errorContainerColor = Color.Transparent,
                            )
                        )
                    }

                    // Espécie e Raça (lado a lado)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Espécie
                        Box(modifier = Modifier.weight(1f)) {
                            Column {
                                Text(
                                    text = "Espécie*",
                                    fontFamily = montserratFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = customColorScheme.surface,
                                    modifier = Modifier.padding(bottom = 1.dp)
                                )
                                ExposedDropdownMenuBox(
                                    expanded = especieExpanded,
                                    onExpandedChange = { especieExpanded = it }
                                ) {
                                    OutlinedTextField(
                                        value = especie,
                                        onValueChange = {},
                                        placeholder = {
                                            Text(
                                                "Selecione",
                                                style = innerInputTextStyle
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(48.dp)
                                            .menuAnchor(),
                                        readOnly = true,
                                        singleLine = true,
                                        shape = MaterialTheme.shapes.small,
                                        textStyle = innerInputTextStyle,
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
                                            unfocusedIndicatorColor = borderColor,
                                            focusedIndicatorColor = borderColor,
                                            errorContainerColor = Color.Transparent,
                                        )
                                    )
                                    ExposedDropdownMenu(
                                        expanded = especieExpanded,
                                        onDismissRequest = { especieExpanded = false }
                                    ) {
                                        especies.forEach { option ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        text = option,
                                                        fontFamily = montserratFontFamily,
                                                        fontWeight = FontWeight.Medium,
                                                        fontSize = 14.sp,
                                                        color = customColorScheme.surface
                                                    )
                                                },
                                                onClick = {
                                                    especie = option
                                                    especieExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Raça
                        Box(modifier = Modifier.weight(1f)) {
                            Column {
                                Text(
                                    text = "Raça*",
                                    fontFamily = montserratFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = customColorScheme.surface,
                                    modifier = Modifier.padding(bottom = 1.dp)
                                )
                                ExposedDropdownMenuBox(
                                    expanded = racaExpanded,
                                    onExpandedChange = { racaExpanded = it }
                                ) {
                                    OutlinedTextField(
                                        value = raca,
                                        onValueChange = {},
                                        placeholder = {
                                            Text(
                                                "Selecione",
                                                style = innerInputTextStyle
                                            )
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(48.dp)
                                            .menuAnchor(),
                                        readOnly = true,
                                        singleLine = true,
                                        shape = MaterialTheme.shapes.small,
                                        textStyle = innerInputTextStyle,
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
                                            unfocusedIndicatorColor = borderColor,
                                            focusedIndicatorColor = borderColor,
                                            errorContainerColor = Color.Transparent,
                                        )
                                    )
                                    ExposedDropdownMenu(
                                        expanded = racaExpanded,
                                        onDismissRequest = { racaExpanded = false }
                                    ) {
                                        racas.forEach { option ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        text = option,
                                                        fontFamily = montserratFontFamily,
                                                        fontWeight = FontWeight.Medium,
                                                        fontSize = 14.sp,
                                                        color = customColorScheme.surface
                                                    )
                                                },
                                                onClick = {
                                                    raca = option
                                                    racaExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Data de Nascimento e Cor (lado a lado)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Data de Nascimento
                        Box(modifier = Modifier.weight(1f)) {
                            Column {
                                Text(
                                    text = "Data de Nascimento*",
                                    fontFamily = montserratFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = customColorScheme.surface,
                                    modifier = Modifier.padding(bottom = 1.dp)
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
                                        dataNascimento = formattedDate
                                    },
                                    placeholder = {
                                        Text(
                                            "__/__/____",
                                            style = innerInputTextStyle
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    singleLine = true,
                                    shape = MaterialTheme.shapes.small,
                                    visualTransformation = DateVisualTransformation(),
                                    textStyle = innerInputTextStyle,
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedTextColor = customColorScheme.onSurface,
                                        unfocusedIndicatorColor = borderColor,
                                        focusedIndicatorColor = borderColor,
                                        errorContainerColor = Color.Transparent,
                                    )
                                )
                            }
                        }

                        // Cor
                        Box(modifier = Modifier.weight(1f)) {
                            Column {
                                Text(
                                    text = "Cor*",
                                    fontFamily = montserratFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = customColorScheme.surface,
                                    modifier = Modifier.padding(bottom = 1.dp)
                                )
                                OutlinedTextField(
                                    value = cor,
                                    onValueChange = { cor = it },
                                    placeholder = {
                                        Text(
                                            "Digite a cor",
                                            style = innerInputTextStyle
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    singleLine = true,
                                    shape = MaterialTheme.shapes.small,
                                    textStyle = innerInputTextStyle,
                                    colors = TextFieldDefaults.colors(
                                        focusedContainerColor = Color.Transparent,
                                        unfocusedContainerColor = Color.Transparent,
                                        focusedTextColor = customColorScheme.onSurface,
                                        unfocusedIndicatorColor = borderColor,
                                        focusedIndicatorColor = borderColor,
                                        errorContainerColor = Color.Transparent,
                                    )
                                )
                            }
                        }
                    }

                    // Porte e Sexo (lado a lado)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Porte
                        Box(modifier = Modifier.weight(1f)) {
                            Column {
                                Text(
                                    text = "Porte*",
                                    fontFamily = montserratFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = customColorScheme.surface,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Column {
                                    portes.forEach { option ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(32.dp)
                                                .selectable(
                                                    selected = (porte == option),
                                                    onClick = { porte = option }
                                                ),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Checkbox(
                                                checked = (porte == option),
                                                onCheckedChange = { porte = option },
                                                colors = CheckboxDefaults.colors(
                                                    checkedColor = customColorScheme.primary,
                                                    uncheckedColor = borderColor
                                                )
                                            )
                                            Text(
                                                text = option,
                                                fontFamily = montserratFontFamily,
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 14.sp,
                                                color = customColorScheme.surface,
                                                modifier = Modifier.padding(start = 4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }

                        // Sexo
                        Box(modifier = Modifier.weight(1f)) {
                            Column {
                                Text(
                                    text = "Sexo*",
                                    fontFamily = montserratFontFamily,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp,
                                    color = customColorScheme.surface,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                Column {
                                    sexos.forEach { option ->
                                        Row(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(32.dp)
                                                .selectable(
                                                    selected = (sexo == option),
                                                    onClick = { sexo = option }
                                                ),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Checkbox(
                                                checked = (sexo == option),
                                                onCheckedChange = { sexo = option },
                                                colors = CheckboxDefaults.colors(
                                                    checkedColor = customColorScheme.primary,
                                                    uncheckedColor = borderColor
                                                )
                                            )
                                            Text(
                                                text = option,
                                                fontFamily = montserratFontFamily,
                                                fontWeight = FontWeight.Medium,
                                                fontSize = 14.sp,
                                                color = customColorScheme.surface,
                                                modifier = Modifier.padding(start = 4.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Observações
                    Column {
                        Text(
                            text = "Observações",
                            fontFamily = montserratFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = 12.sp,
                            color = customColorScheme.surface,
                            modifier = Modifier.padding(bottom = 1.dp)
                        )
                        OutlinedTextField(
                            value = observacoes,
                            onValueChange = { observacoes = it },
                            placeholder = {
                                Text(
                                    "Digite outras informações sobre o pet (alergias, condições de saúde...)",
                                    style = innerInputTextStyle
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = false,
                            minLines = 3,
                            shape = MaterialTheme.shapes.small,
                            textStyle = innerInputTextStyle,
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                focusedTextColor = customColorScheme.onSurface,
                                unfocusedIndicatorColor = borderColor,
                                focusedIndicatorColor = borderColor,
                                errorContainerColor = Color.Transparent,
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botão Cadastrar
                    Button(
                        onClick = { /* TODO */ },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(50.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = customColorScheme.primary)
                    ) {
                        Text(
                            text = "Cadastrar",
                            style = buttonTextStyle,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PetRegisterScreenPreview() {
    PetRegisterScreen(rememberNavController())
}