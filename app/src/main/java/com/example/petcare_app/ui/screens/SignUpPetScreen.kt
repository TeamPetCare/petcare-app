package com.example.petcare_app.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petcare_app.data.viewmodel.SignUpViewModel
import com.example.petcare_app.ui.components.buttons.BackButton
import com.example.petcare_app.ui.components.formFields.CustomDropdown
import com.example.petcare_app.ui.components.formFields.inputFields.CustomTextInput
import com.example.petcare_app.ui.components.formFields.inputFields.CustomTextLongInput
import com.example.petcare_app.ui.components.formFields.inputFields.DateInput
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.paragraphTextStyle
import com.example.petcare_app.ui.theme.titleTextStyle

//@Composable
//fun SignUpPetScreenPreview() {
//    SignUpPetScreen()
//}

@Composable
fun SignUpPetScreen(navController: NavController, viewModel: SignUpViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 20.dp, bottom = 30.dp, top = 15.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var isFormSubmitted by remember { mutableStateOf(false) }

        // Variáveis de erro
        var nomePetErro by remember { mutableStateOf(false) }
        var especiePetErro by remember { mutableStateOf(false) }
        var racaPetErro by remember { mutableStateOf(false) }
        var dtNascPetErro by remember { mutableStateOf(false) }
        var corPetErro by remember { mutableStateOf(false) }
        var portePetErro by remember { mutableStateOf(false) }
        var sexoPetErro by remember { mutableStateOf(false) }

        BackButton(navController = navController)
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Compartilhe um pouco sobre seus pets!",
            style = titleTextStyle,
            color = customColorScheme.primary,
            textAlign = TextAlign.Center,
            fontSize = 34.sp
        )
        Text(
            modifier = Modifier
                .padding(8.dp),
            text = "Essas informações ajudam a personalizar sua experiência no aplicativo.",
            style = paragraphTextStyle,
            color = customColorScheme.primary,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "* Campos obrigatórios.",
            style = paragraphTextStyle,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            color = customColorScheme.primary,
            textAlign = TextAlign.Center
        )

        // Campos do formulário
        viewModel.pets.forEachIndexed { index, pet ->

            Spacer(modifier = Modifier.height(18.dp))
            CustomTextInput(
                value = pet.nome,
                onValueChange = { novoNome ->
                    val updatedPet = pet.copy(nome = novoNome)
                    viewModel.updatePet(index, updatedPet)
                },
                label = "Nome",
                placeholder = "Digite o nome do pet",
                modifier = Modifier.fillMaxWidth(),
                isFormSubmitted = isFormSubmitted,
                isError = nomePetErro,
                msgErro = "*Insira o nome do pet",
                isRequired = true
            )

            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CustomDropdown(
                    value = pet.especie,
                    onValueChange = { novaEspecie ->
                        val updatedPet = pet.copy(especie = novaEspecie)
                        viewModel.updatePet(index, updatedPet)
                    },
                    label = "Espécie",
                    placeholder = "Selecione",
                    options = listOf("Cachorro", "Gato", "Pássaro", "Outro"),
                    modifier = Modifier.fillMaxWidth(.48f),
                    isFormSubmitted = isFormSubmitted,
                    isError = especiePetErro,
                    isRequired = true
                )
                CustomTextInput(
                    value = pet.raca,
                    onValueChange = { novaRaca ->
                        val updatedPet = pet.copy(raca = novaRaca)
                        viewModel.updatePet(index, updatedPet)
                    },
                    label = "Raça",
                    placeholder = "Digite a raça do pet",
                    modifier = Modifier.fillMaxWidth(1f),
                    isFormSubmitted = isFormSubmitted,
                    isError = racaPetErro,
                    msgErro = "*Selecione uma raça",
                    isRequired = true
                )
            }


            Spacer(modifier = Modifier.height(18.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                DateInput(
                    value = pet.dataNascimento,
                    onValueChange = { novaDtNasc ->
                        val updatedPet = pet.copy(dataNascimento = novaDtNasc)
                        viewModel.updatePet(index, updatedPet)
                    },
                    label = "Data de Nascimento",
                    placeholder = "__ / __ / ____",
                    modifier = Modifier.fillMaxWidth(.48f),
                    isFormSubmitted = isFormSubmitted,
                    isError = dtNascPetErro,
                    msgErro = "*Insira a data de nascimento corretamente",
                    isRequired = true
                )
                CustomTextInput(
                    value = pet.cor,
                    onValueChange = { novaCor ->
                        val updatedPet = pet.copy(cor = novaCor)
                        viewModel.updatePet(index, updatedPet)
                    },
                    label = "Cor",
                    placeholder = "Digite a cor do pelo",
                    modifier = Modifier.fillMaxWidth(1f),
                    isFormSubmitted = isFormSubmitted,
                    isError = corPetErro,
                    msgErro = "*Insira a cor da pelagem do pet",
                    isRequired = true
                )
            }

            CustomTextLongInput(
                value = pet.observacoes,
                onValueChange = { novaObs ->
                    val updatedPet = pet.copy(observacoes = novaObs)
                    viewModel.updatePet(index, updatedPet)
                },
                label = "Observações",
                placeholder = "Digite outras informações sobre o pet (alergias, condições de saúde...)",
                modifier = Modifier.fillMaxWidth(),
                isFormSubmitted = isFormSubmitted,
                isRequired = false
            )
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun SignUpPetPreview() {
//    SignUpPetScreenPreview()
//}