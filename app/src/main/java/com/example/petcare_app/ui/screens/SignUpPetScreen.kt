    package com.example.petcare_app.ui.screens
    
    import android.util.Log
    import android.widget.Toast
    import androidx.compose.foundation.background
    import androidx.compose.foundation.border
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxHeight
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.layout.width
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
    import androidx.compose.material.icons.filled.Add
    import androidx.compose.material.icons.filled.Delete
    import androidx.compose.material.icons.filled.Edit
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults.buttonColors
    import androidx.compose.material3.Icon
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.LaunchedEffect
    import androidx.compose.runtime.collectAsState
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.rememberCoroutineScope
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.res.colorResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.tooling.preview.Preview
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.navigation.NavController
    import androidx.navigation.compose.rememberNavController
    import com.example.petcare_app.data.model.Race
    import com.example.petcare_app.data.model.Size
    import com.example.petcare_app.data.model.Specie
    import com.example.petcare_app.data.viewmodel.Pet
    import com.example.petcare_app.data.viewmodel.SignUpViewModel
    import com.example.petcare_app.navigation.Screen
    import com.example.petcare_app.ui.components.buttons.BackButton
    import com.example.petcare_app.ui.components.formFields.CustomDropdown
    import com.example.petcare_app.ui.components.formFields.CustomRadioBox
    import com.example.petcare_app.ui.components.formFields.inputFields.CustomTextInput
    import com.example.petcare_app.ui.components.formFields.inputFields.CustomTextLongInput
    import com.example.petcare_app.ui.components.formFields.inputFields.DateInput
    import com.example.petcare_app.ui.components.formFields.inputFields.NumberInput
    import com.example.petcare_app.ui.components.signUpPetScreen.AddPet
    import com.example.petcare_app.ui.theme.buttonTextStyle
    import com.example.petcare_app.ui.theme.customColorScheme
    import com.example.petcare_app.ui.theme.paragraphTextStyle
    import com.example.petcare_app.ui.theme.titleTextStyle
    import kotlinx.coroutines.launch

    @Composable
    fun petFormComponent(
        viewModel: SignUpViewModel,
        index: Int,
        pet: Pet,
        isFormSubmitted: Boolean,
        isPetFormActive: (Boolean) -> Unit,
        navController: NavController,
        racas: List<Race>,
        especies: List<Specie>,
        tamanhos: List<Size>
    ) {
        var nomePetErro by remember { mutableStateOf(false) }
        var especiePetErro by remember { mutableStateOf(false) }
        var racaPetErro by remember { mutableStateOf(false) }
        var dtNascPetErro by remember { mutableStateOf(false) }
        var pesoPetErro by remember { mutableStateOf(false) }
        var corPetErro by remember { mutableStateOf(false) }
        var portePetErro by remember { mutableStateOf(false) }
        var sexoPetErro by remember { mutableStateOf(false) }

        var petState by remember { mutableStateOf(pet) }

        fun validateForm(): Boolean {
            nomePetErro = petState.nome.length < 2
            especiePetErro = petState.especie == null
            racaPetErro = petState.raca == null
            dtNascPetErro = petState.dataNascimento.isEmpty()
            pesoPetErro = petState.peso.isEmpty()
            corPetErro = petState.cor.length < 4
            portePetErro = petState.porte == null
            sexoPetErro = petState.sexo.isEmpty()

            return !(nomePetErro || especiePetErro || racaPetErro || dtNascPetErro || pesoPetErro || corPetErro || portePetErro || sexoPetErro)
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomTextInput(
                value = petState.nome,
                onValueChange = { novoNome ->
                    petState = petState.copy(nome = novoNome)
                    viewModel.updatePet(index, petState)
                },
                label = "Nome",
                placeholder = "Digite o nome do pet",
                modifier = Modifier.fillMaxWidth(),
                isFormSubmitted = isFormSubmitted,
                isError = nomePetErro,
                msgErro = "*Insira o nome do pet",
                isRequired = true,
            )

            Spacer(modifier = Modifier.height(10.dp))

            DateInput(
                value = petState.dataNascimento,
                onValueChange = { novaDtNasc ->
                    petState = petState.copy(dataNascimento = novaDtNasc)  // Atualiza o petState
                    viewModel.updatePet(index, petState)  // Atualiza no ViewModel
                },
                label = "Data de Nascimento",
                placeholder = "__ / __ / ____",
                modifier = Modifier.fillMaxWidth(),
                isFormSubmitted = isFormSubmitted,
                isError = dtNascPetErro,
                msgErro = "*Insira a data de nascimento corretamente",
                isRequired = true
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    CustomDropdown(
                        value = petState.especie?.let { specieId ->
                            especies.find { it.id == specieId }?.name ?: ""
                        } ?: "",
                        onValueChange = { novaEspecie ->
                            val especieSelecionada = especies.find { it.name == novaEspecie }
                            petState = petState.copy(especie = especieSelecionada?.id) // Atualiza o petState
                            viewModel.updatePet(index, petState)  // Atualiza no ViewModel
                        },
                        label = "Espécie",
                        placeholder = "Selecione",
                        options = especies.map {it.name},
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = especiePetErro,
                        isRequired = true
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    CustomDropdown(
                        value = petState.raca?.let { racaId ->
                            racas.find { it.id == racaId }?.raceType ?: ""
                        } ?: "",
                        onValueChange = { novaRaca ->
                            val racaSelecionada = racas.find { it.raceType == novaRaca }
                            petState = petState.copy(raca = racaSelecionada?.id) // Atualiza o petState
                            viewModel.updatePet(index, petState)  // Atualiza no ViewModel
                        },
                        label = "Raça",
                        placeholder = "Selecione",
                        options = racas.map {it.raceType},
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = racaPetErro,
                        isRequired = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    NumberInput(
                        value = petState.peso,
                        onValueChange = { novoPeso ->
                            petState = petState.copy(peso = novoPeso)  // Atualiza o petState
                            viewModel.updatePet(index, petState)  // Atualiza no ViewModel
                        },
                        label = "Peso",
                        placeholder = "Digite o peso estimado",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = pesoPetErro,
                        msgErro = "*Insira o peso estimadodo pet",
                        isRequired = true
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    CustomTextInput(
                        value = petState.cor,
                        onValueChange = { novaCor ->
                            petState = petState.copy(cor = novaCor)  // Atualiza o petState
                            viewModel.updatePet(index, petState)  // Atualiza no ViewModel
                        },
                        label = "Cor",
                        placeholder = "Digite a cor da pelagem",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = corPetErro,
                        msgErro = "*Insira a cor da pelagem do pet",
                        isRequired = true
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Column(modifier = Modifier.weight(1f)) {
                    CustomRadioBox(
                        selectedOption = petState.porte?.let { porteId ->
                            tamanhos.find { it.id == porteId }?.sizeType ?: ""
                        } ?: "",
                        onOptionSelected = { newPorte ->
                            val porteSelecionado = tamanhos.find { it.sizeType == newPorte }
                            petState = petState.copy(porte = porteSelecionado?.id)  // Atualiza o petState
                            viewModel.updatePet(index, petState)
                        },
                        label = "Porte",
                        options = tamanhos.map { it.sizeType },
                        isFormSubmitted = isFormSubmitted,
                        isError = portePetErro,
                        isRequired = true
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    CustomRadioBox(
                        selectedOption = petState.sexo,
                        onOptionSelected = { newSexo ->
                            petState = petState.copy(sexo = newSexo)  // Atualiza o petState
                            viewModel.updatePet(index, petState)
                        },
                        label = "Sexo",
                        options = listOf("Masculino", "Feminino"),
                        isFormSubmitted = isFormSubmitted,
                        isError = sexoPetErro,
                        isRequired = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            CustomTextLongInput(
                value = petState.observacoes,
                onValueChange = { novaObs ->
                    petState = petState.copy(observacoes = novaObs)  // Atualiza o petState
                    viewModel.updatePet(index, petState)
                },
                label = "Observações",
                placeholder = "Digite outras informações sobre o pet (alergias, condições de saúde...)",
                modifier = Modifier.fillMaxWidth(),
                isFormSubmitted = isFormSubmitted,
                isRequired = false
            )

            Spacer(modifier = Modifier.height(15.dp))
            //      Botão de envio
            Button(
                onClick = {
                    if (validateForm()) {
                        viewModel.addPet(petState)
                        petState = Pet()
                        isPetFormActive(false)
                    }
                },
                colors = buttonColors(
                    containerColor = if (isFormSubmitted) customColorScheme.error else customColorScheme.secondary,
                    contentColor = if (isFormSubmitted) Color.White else customColorScheme.primary,
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Próximo",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = buttonTextStyle
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Seta para a direita"
                    )
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            //      Botão de pular cadastro
            Button(
                onClick = {
                    navController.navigate("loadingtoapphome")
                },
                colors = buttonColors(
                    containerColor = customColorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Gostaria de cadastrar depois",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = buttonTextStyle
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Seta para a direita"
                    )
                }
            }
        }
    }


    @Composable
    fun SignUpPetScreen(navController: NavController, viewModel: SignUpViewModel) {
        var isFormSubmitted by remember { mutableStateOf(false) }
        val pets by viewModel.pets.collectAsState()
        var isPetFormActive by remember { mutableStateOf(true) }
        var currentPetIndex by remember { mutableStateOf(pets.size) }
        var petState by remember { mutableStateOf(Pet()) }  // Estado do pet atual

        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current

        LaunchedEffect(Unit) {
            viewModel.getSpecies(
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJsb2dpbi1hdXRoLWFwaSIsInN1YiI6ImNpcmlsb0Rvbm9AZ21haWwuY29tIiwicm9sZSI6IlJPTEVfQURNSU4iLCJ1c2VySWQiOjEzLCJleHAiOjE3NDUzMDc5MTZ9.NWi6WBSSCU63ImOyHM-aI2dSxKHW80CHyUIr2jRrEnI"
            )
            viewModel.getRaces(
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJsb2dpbi1hdXRoLWFwaSIsInN1YiI6ImNpcmlsb0Rvbm9AZ21haWwuY29tIiwicm9sZSI6IlJPTEVfQURNSU4iLCJ1c2VySWQiOjEzLCJleHAiOjE3NDUzMDc5MTZ9.NWi6WBSSCU63ImOyHM-aI2dSxKHW80CHyUIr2jRrEnI"
            )
            viewModel.getSizes(
                token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJsb2dpbi1hdXRoLWFwaSIsInN1YiI6ImNpcmlsb0Rvbm9AZ21haWwuY29tIiwicm9sZSI6IlJPTEVfQURNSU4iLCJ1c2VySWQiOjEzLCJleHAiOjE3NDUzMDc5MTZ9.NWi6WBSSCU63ImOyHM-aI2dSxKHW80CHyUIr2jRrEnI"
            )
        }

        val especies = viewModel.species.collectAsState().value
        val racas = viewModel.races.collectAsState().value
        val tamanhos = viewModel.sizes.collectAsState().value

        // Função para resetar o formulário
        val resetForm: () -> Unit = {
            petState = Pet()
            Log.d("PETS_DEBUG", "Lista de pets: ${pets.map { it.nome }}")
            viewModel.addPet(petState)
            currentPetIndex = pets.size

        }

        // Função para enviar dados
        fun sendData() {
            pets.forEachIndexed { index, pet ->
                Log.d("FORM_SIGNUP", "Dados do Pet $index: " +
                        " ${pet}")
            }

            Toast.makeText(context, "ANTES Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()

            coroutineScope.launch {
                viewModel.signUpUser(
                    token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJsb2dpbi1hdXRoLWFwaSIsInN1YiI6ImNpcmlsb0Rvbm9AZ21haWwuY29tIiwicm9sZSI6IlJPTEVfQURNSU4iLCJ1c2VySWQiOjEzLCJleHAiOjE3NDUzMDc5MTZ9.NWi6WBSSCU63ImOyHM-aI2dSxKHW80CHyUIr2jRrEnI",
                    onSuccess = {
                        Toast.makeText(context, "DEPOIS Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        navController.navigate(Screen.HomeApp.route)

                    },
                    onError = { mensagemErro ->
                        Toast.makeText(context, "Erro: $mensagemErro", Toast.LENGTH_SHORT).show()
                    }
                )
            }

        }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxHeight()
                    .padding(
                        start = 20.dp,
                        bottom = 20.dp,
                        top = 15.dp,
                        end = 20.dp
                    )
                    .let { if (isPetFormActive) it.verticalScroll(rememberScrollState()) else it },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
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
                    modifier = Modifier.padding(8.dp),
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
                Spacer(modifier = Modifier.height(25.dp))

                if (isPetFormActive) {
                    petFormComponent(
                        viewModel,
                        index = currentPetIndex,
                        pet = petState,
                        isFormSubmitted = isFormSubmitted,
                        isPetFormActive = { isPetFormActive = it },
                        navController = navController,
                        racas = racas,
                        especies = especies,
                        tamanhos = tamanhos
                    )
                } else {
                    AddPet (
                        navController = navController,
                        pets = pets,
                        racas = racas,
                        isPetFormActive = { isPetFormActive = it },
                        resetForm = resetForm,
                        sendData = { sendData() }
                    )
                }
            }
    }

    @Preview(showBackground = true)
    @Composable
    fun SignUpPetScreenPreview() {
        val navController = rememberNavController()
        val viewModel = SignUpViewModel()
        SignUpPetScreen(navController = navController, viewModel = viewModel)
    }
