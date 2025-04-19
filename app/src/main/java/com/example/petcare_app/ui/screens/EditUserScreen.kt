package com.example.petcare_app.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.SupervisedUserCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.R
import com.example.petcare_app.data.viewmodel.EditUserViewModel
import com.example.petcare_app.data.viewmodel.SignUpViewModel
import com.example.petcare_app.ui.components.formFields.inputFields.CepInput
import com.example.petcare_app.ui.components.formFields.inputFields.CustomTextInput
import com.example.petcare_app.ui.components.formFields.inputFields.EmailInput
import com.example.petcare_app.ui.components.formFields.inputFields.MaskedInput
import com.example.petcare_app.ui.components.formFields.inputFields.PasswordInput
import com.example.petcare_app.ui.components.formFields.inputFields.isValidCPF
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable
import com.example.petcare_app.ui.components.layouts.ImageEditorComposable
import com.example.petcare_app.ui.components.layouts.WhiteCanvas
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.paragraphTextStyle
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
private fun EditUserScreenPreview() {
    val navControllerMock = rememberNavController()
    val editUserViewModelMock = SignUpViewModel()
    EditUserScreen(navControllerMock, editUserViewModelMock)
}

@Composable
fun EditUserScreen(navController: NavController, signUpViewModel: SignUpViewModel) {
    var editUserViewModel: EditUserViewModel = viewModel()

    val user = editUserViewModel.editUser

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
          editUserViewModel.getUserById(
               "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJsb2dpbi1hdXRoLWFwaSIsInN1YiI6ImNpcmlsb0Rvbm9AZ21haWwuY29tIiwicm9sZSI6IlJPTEVfQURNSU4iLCJ1c2VySWQiOjEzLCJleHAiOjE3NDUwNjU5MjR9.--eifXHRt31iH7GEWhkG0a2rAw74qN_74GPbBmBDhjw",
               1
         )
        }


    var isFormSubmitted by remember { mutableStateOf(false) }

//  Variáveis de erro
    var nomeErro by remember { mutableStateOf(false) }
    var emailErro by remember { mutableStateOf(false) }
    var celularErro by remember { mutableStateOf(false) }
    var cpfErro by remember { mutableStateOf(false) }
    var senhaErro by remember { mutableStateOf(false) }
    var novaSenhaError by remember { mutableStateOf(false) }
    var confirmarSenhaErro by remember { mutableStateOf(false) }
    var cepErro by remember { mutableStateOf(false) }
    var logradouroErro by remember { mutableStateOf(false) }
    var bairroErro by remember { mutableStateOf(false) }
    var numeroErro by remember { mutableStateOf(false) }
    var cidadeErro by remember { mutableStateOf(false) }

    //  Função de validação
    fun validateForm(): Boolean {
        nomeErro = user.nomeCompleto.length < 5
        cpfErro = user.cpf.isEmpty() || !isValidCPF(user.cpf)
        emailErro =
            user.email.isEmpty() || !user.email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex())
        celularErro =
            user.celular.isEmpty() || !user.celular.matches("^\\(?\\d{2}\\)?\\s?9\\d{4}-?\\d{4}\$".toRegex())
        senhaErro =
            user.senha.isEmpty() || !user.senha.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$".toRegex())
        confirmarSenhaErro = user.novaSenha.isNotEmpty() && (user.confirmarSenha.isEmpty() || user.confirmarSenha != user.novaSenha)
        cepErro = user.cep.length < 8
        logradouroErro = user.logradouro.length < 5
        bairroErro = user.bairro.length < 5
        numeroErro = user.numero.isEmpty()
        cidadeErro = user.cidade.length < 5

        return !(nomeErro || cpfErro || emailErro || celularErro || senhaErro || confirmarSenhaErro || cepErro || logradouroErro || bairroErro || numeroErro || cidadeErro)
    }

    //  Função para enviar os dados para o banco
    fun sendData() {
        Log.d(
            "FORM_SIGNUP", "Dados: " +
                    " Nome - ${user.nomeCompleto}" +
                    " CPF - ${user.cpf}" +
                    " E-mail - ${user.email}" +
                    " Celular - ${user.celular}" +
                    " Senha - ${user.senha}" +
                    " Nova Senha - ${user.novaSenha}" +
                    " Confirmar Senha - ${user.confirmarSenha}" +
                    " CEP - ${user.cep}" +
                    " Logradouro - ${user.logradouro}" +
                    " Bairro - ${user.bairro}" +
                    " Complemento - ${user.complemento}" +
                    " Cidade - ${user.cidade}")
    }

    // var para ver se está no modo de edição
    var editarInfos by remember { mutableStateOf(false) }

    //função para resetar campos ao clicar no botão cancelar
    fun clearForm() {

        editUserViewModel.editUser = user;

//        editUserViewModel.editUser = editUserViewModel.editUser.copy(
//            nomeCompleto = "",
//            cpf = "",
//            email = "",
//            celular = "",
//            senha = "",
//            confirmarSenha = "",
//            novaSenha = "",
//            cep = "",
//            logradouro = "",
//            bairro = "",
//            numero = "",
//            complemento = "",
//            cidade = ""
//        )

        isFormSubmitted = false
        nomeErro = false
        cpfErro = false
        emailErro = false
        celularErro = false
        senhaErro = false
        confirmarSenhaErro = false
        novaSenhaError = false
        cepErro = false
        logradouroErro = false
        bairroErro = false
        numeroErro = false
        cidadeErro = false
    }


    Scaffold(topBar = {
        HeaderComposable(
            navController,
            userName = "Usuário"
        )
    }, bottomBar = { GadjetBarComposable(navController) }) { it ->
        Column(Modifier.background(Color(0, 84, 114)).padding(it)) {
            WhiteCanvas(
                modifier = Modifier.fillMaxHeight(),
                icon = Icons.Outlined.SupervisedUserCircle,
                "Editar Perfil",
                true,
                navController = navController
            ) {

//  Tela de Inscrição - Sobre o Usuário
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 30.dp, top = 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    ImageEditorComposable()

//      Campos do formulário
                    Spacer(modifier = Modifier.height(18.dp))
                    CustomTextInput(
                        value = user.nomeCompleto,
                        onValueChange = { novoNome ->
                            editUserViewModel.updateEditUser { copy(nomeCompleto = novoNome) }
                        },
                        label = "Nome Completo",
                        placeholder = "Digite seu nome completo",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = nomeErro,
                        isRequired = true,
                        enabled = editarInfos
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    MaskedInput(
                        label = "CPF",
                        value = user.cpf,
                        onValueChange = { novoCpf ->
                            editUserViewModel.updateEditUser { copy(cpf = novoCpf) }
                        },
                        placeholder = "___.___.___-__",
                        type = "CPF",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = cpfErro,
                        enabled = editarInfos
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    EmailInput(
                        label = "E-mail",
                        value = user.email,
                        onValueChange = { novoEmail ->
                            editUserViewModel.updateEditUser { copy(email = novoEmail) }
                        },
                        placeholder = "exemplo@gmail.com",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = emailErro,
                        enabled = editarInfos
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    MaskedInput(
                        label = "Celular",
                        value = user.celular,
                        onValueChange = { novoCelular ->
                            editUserViewModel.updateEditUser { copy(celular = novoCelular) }
                        },
                        placeholder = "(__) ____-____",
                        type = "Celular",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = celularErro,
                        enabled = editarInfos
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    PasswordInput(
                        label = "Senha Atual",
                        value = user.senha,
                        onValueChange = { senhaAtual ->
                            editUserViewModel.updateEditUser { copy(senha = senhaAtual) }
                        },
                        placeholder = "Senha atual",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = senhaErro,
                        confirmarSenha = false,
                        enabled = editarInfos
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Esqueci minha senha",
                        fontWeight = FontWeight.Normal,
                        color = customColorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable {
                                // função futura
                            }
                    )
                    if (editarInfos) {
                        Spacer(modifier = Modifier.height(12.dp))
                        PasswordInput(
                            label = "Nova senha",
                            value = user.novaSenha,
                            onValueChange = { novaSenha ->
                                editUserViewModel.updateEditUser { copy(novaSenha = novaSenha) }
                            },
                            placeholder = "Digite a senha",
                            modifier = Modifier.fillMaxWidth(),
                            isFormSubmitted = isFormSubmitted,
                            isError = novaSenhaError,
                            confirmarSenha = false,
                            enabled = editarInfos
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        PasswordInput(
                            label = "Confirme a senha",
                            value = user.confirmarSenha,
                            onValueChange = { confirmarSenha ->
                                editUserViewModel.updateEditUser { copy(confirmarSenha = confirmarSenha) }
                            },
                            placeholder = "Repita a senha",
                            modifier = Modifier.fillMaxWidth(),
                            isFormSubmitted = isFormSubmitted,
                            isError = confirmarSenhaErro,
                            confirmarSenha = true,
                            enabled = editarInfos
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Endereço",
                        style = paragraphTextStyle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    CepInput(
                        label = "CEP",
                        value = user.cep,
                        onValueChange = { novoCep ->
                            editUserViewModel.updateEditUser { copy(cep = novoCep) }
                        },
                        placeholder = "Digite seu CEP",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = cepErro,
                        onAddressRetrieved = { newLogradouro, newBairro, newCidade ->
                            editUserViewModel.updateEditUser { copy(logradouro = newLogradouro) }
                            editUserViewModel.updateEditUser { copy(bairro = newBairro) }
                            editUserViewModel.updateEditUser { copy(cidade = newCidade) }
                        },
                        addressRetrieved = "${user.logradouro}",
                        enabled = editarInfos
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextInput(
                        value = user.logradouro,
                        onValueChange = { novoLogradouro ->
                            editUserViewModel.updateEditUser { copy(logradouro = novoLogradouro) }
                        },
                        label = "Logradouro",
                        placeholder = "Digite o logradouro (rua, avenida, número, etc.)",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = logradouroErro,
                        isRequired = true,
                        enabled = editarInfos
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextInput(
                        value = user.bairro,
                        onValueChange = { novoBairro ->
                            editUserViewModel.updateEditUser { copy(bairro = novoBairro) }
                        },
                        label = "Bairro",
                        placeholder = "Digite o nome do bairro",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = bairroErro,
                        isRequired = true,
                        enabled = editarInfos
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextInput(
                        value = user.numero,
                        onValueChange = { novoNumero ->
                            editUserViewModel.updateEditUser { copy(numero = novoNumero) }
                        },
                        label = "Número",
                        placeholder = "Digite o número do endereço",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = numeroErro,
                        msgErro = "*Insira o número do endereço corretamente.",
                        isRequired = true,
                        enabled = editarInfos
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextInput(
                        value = user.complemento,
                        onValueChange = { novoComplemento ->
                            editUserViewModel.updateEditUser { copy(complemento = novoComplemento) }
                        },
                        label = "Complemento",
                        placeholder = "Digite o complemento (Apartamento, bloco)",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isRequired = false,
                        enabled = editarInfos
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextInput(
                        value = user.cidade,
                        onValueChange = { novaCidade ->
                            editUserViewModel.updateEditUser { copy(cidade = novaCidade) }
                        },
                        label = "Cidade",
                        placeholder = "Digite o nome da cidade",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = cidadeErro,
                        isRequired = true,
                        enabled = editarInfos
                    )


                    if (editarInfos) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    editarInfos = false
                                    isFormSubmitted = false

                                    clearForm()
                                        coroutineScope.launch {
                                             editUserViewModel.getUserById(
                                                 "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJsb2dpbi1hdXRoLWFwaSIsInN1YiI6ImNpcmlsb0Rvbm9AZ21haWwuY29tIiwicm9sZSI6IlJPTEVfQURNSU4iLCJ1c2VySWQiOjEzLCJleHAiOjE3NDUwNjU5MjR9.--eifXHRt31iH7GEWhkG0a2rAw74qN_74GPbBmBDhjw",
                                                 1
                                             )
                                        }
                                },
                                colors = buttonColors(
                                    containerColor = Color(109, 124, 132),
                                    contentColor = Color.White,
                                )
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(0.30f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                        contentDescription = "Seta para a esquerda"
                                    )
                                    Text(
                                        text = "Cancelar",
                                        modifier = Modifier,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                            Button(
                                onClick = {
                                    if (validateForm()) {
                                        sendData()
                                        editarInfos = false
                                    } else isFormSubmitted = true
                                },
                                colors = buttonColors(
                                    containerColor = if (isFormSubmitted) customColorScheme.error else customColorScheme.primary,
                                    contentColor = Color.White,
                                )
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(0.9f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Salvar Informações",
                                        modifier = Modifier,
                                        textAlign = TextAlign.Center,
                                    )
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        contentDescription = "Seta para a direita"
                                    )
                                }
                            }
                        }

                    } else {
//      Botão de edição
                        Spacer(modifier = Modifier.height(15.dp))
                        Button(
                            onClick = {
                                editarInfos = true
                            },
                            colors = buttonColors(
                                containerColor = customColorScheme.primary,
                                contentColor = Color.White,
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = "Editar Informações",
                                    modifier = Modifier,
                                    textAlign = TextAlign.Center,
                                )
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = "Seta para a direita"
                                )
                            }
                        }
                    }
                }
            }
        }
    }


}
