package com.example.petcare_app.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.R
import com.example.petcare_app.navigation.EditUserViewModel
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

@Preview(showBackground = true)
@Composable
private fun EditUserScreenPreview() {
    val navControllerMock = rememberNavController()
    val editUserViewModelMock = EditUserViewModel()
    EditUserScreen(navControllerMock, editUserViewModelMock)
}

@Composable
fun EditUserScreen(navController: NavController, viewModel: EditUserViewModel) {
    val nomeCompleto = viewModel.nomeCompleto
    val cpf = viewModel.cpf
    val email = viewModel.email
    val celular = viewModel.celular
    val senha = viewModel.senha
    val novaSenha = viewModel.novaSenha
    val confirmarSenha = viewModel.confirmarSenha
    val cep = viewModel.cep
    val logradouro = viewModel.logradouro
    val bairro = viewModel.bairro
    val complemento = viewModel.complemento
    val cidade = viewModel.cidade

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
    var cidadeErro by remember { mutableStateOf(false) }

    //  Função de validação
    fun validateForm(): Boolean {
        nomeErro = nomeCompleto.length < 5
        cpfErro = cpf.isEmpty() || !isValidCPF(cpf)
        emailErro =
            email.isEmpty() || !email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex())
        celularErro =
            celular.isEmpty() || !celular.matches("^\\(?\\d{2}\\)?\\s?9\\d{4}-?\\d{4}\$".toRegex())
        senhaErro =
            senha.isEmpty() || !senha.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$".toRegex())
        confirmarSenhaErro = confirmarSenha.isEmpty() || confirmarSenha != novaSenha
        cepErro = cep.length < 8
        logradouroErro = logradouro.length < 5
        bairroErro = bairro.length < 5
        cidadeErro = cidade.length < 5

        return !(nomeErro || cpfErro || emailErro || celularErro || senhaErro || confirmarSenhaErro || cepErro || logradouroErro || bairroErro || cidadeErro)
    }

    //  Função para enviar os dados para o banco
    fun sendData() {
        Log.d(
            "FORM_SIGNUP", "Dados: " +
                    " Nome - $nomeCompleto" +
                    " CPF - $cpf" +
                    " E-mail - $email" +
                    " Celular - $celular" +
                    " Senha - $novaSenha" +
                    " Confirmar Senha - $confirmarSenha" +
                    " CEP - $cep" +
                    " Logradouro - $logradouro" +
                    " Bairro - $bairro" +
                    " Complemento - $complemento" +
                    " Cidade - $cidade"
        )
    }

    // var para ver se está no modo de edição
    var editarInfos = remember { mutableStateOf(false) }

    //função para resetar campos ao clicar no botão cancelar
    fun clearForm() {
        viewModel.nomeCompleto = ""
        viewModel.cpf = ""
        viewModel.email = ""
        viewModel.celular = ""
        viewModel.senha = ""
        viewModel.confirmarSenha = ""
        viewModel.cep = ""
        viewModel.logradouro = ""
        viewModel.bairro = ""
        viewModel.complemento = ""
        viewModel.cidade = ""

        isFormSubmitted = false

        nomeErro = false
        cpfErro = false
        emailErro = false
        celularErro = false
        senhaErro = false
        confirmarSenhaErro = false
        cepErro = false
        logradouroErro = false
        bairroErro = false
        cidadeErro = false
    }

    Scaffold(topBar = {
        HeaderComposable(
            userName = "Usuário",
            profileImageUrl = "https://placekitten.com/200/200"
        )
    },
        bottomBar = { GadjetBarComposable() }) { it ->
        Column(Modifier.background(Color(0, 84, 114)).padding(it)) {
            WhiteCanvas(
                modifier = Modifier.fillMaxHeight(),
                icon = ImageVector.vectorResource(R.drawable.ic_no_profile_picture),
                iconWeight = 30f,
                "Editar Perfil",
                true) {

//  Tela de Inscrição - Sobre o Usuário
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(start = 20.dp, bottom = 30.dp, top = 15.dp, end = 20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    ImageEditorComposable()

//      Campos do formulário
                    Spacer(modifier = Modifier.height(18.dp))
                    CustomTextInput(
                        value = nomeCompleto,
                        onValueChange = { viewModel.nomeCompleto = it },
                        label = "Nome Completo",
                        placeholder = "Digite seu nome completo",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = nomeErro,
                        isRequired = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    MaskedInput(
                        label = "CPF",
                        value = cpf,
                        onValueChange = { viewModel.cpf = it },
                        placeholder = "___.___.___-__",
                        type = "CPF",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = cpfErro
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    EmailInput(
                        label = "E-mail",
                        value = email,
                        onValueChange = { viewModel.email = it },
                        placeholder = "exemplo@gmail.com",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = emailErro
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    MaskedInput(
                        label = "Celular",
                        value = celular,
                        onValueChange = { viewModel.celular = it },
                        placeholder = "(__) ____-____",
                        type = "Celular",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = celularErro
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    PasswordInput(
                        label = "Senha Atual",
                        value = senha,
                        onValueChange = { viewModel.senha = it },
                        placeholder = "Senha atual",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = senhaErro,
                        confirmarSenha = false
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
                    if (editarInfos.value) {
                        Spacer(modifier = Modifier.height(12.dp))
                        PasswordInput(
                            label = "Nova senha",
                            value = novaSenha,
                            onValueChange = { viewModel.novaSenha = it },
                            placeholder = "Digite a senha",
                            modifier = Modifier.fillMaxWidth(),
                            isFormSubmitted = isFormSubmitted,
                            isError = novaSenhaError,
                            confirmarSenha = false
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        PasswordInput(
                            label = "Confirme a senha",
                            value = confirmarSenha,
                            onValueChange = { viewModel.confirmarSenha = it },
                            placeholder = "Repita a senha",
                            modifier = Modifier.fillMaxWidth(),
                            isFormSubmitted = isFormSubmitted,
                            isError = confirmarSenhaErro,
                            confirmarSenha = true
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
                        value = cep,
                        onValueChange = { viewModel.cep = it },
                        placeholder = "Digite seu CEP",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = cepErro,
                        onAddressRetrieved = { newLogradouro, newBairro, newCidade ->
                            viewModel.logradouro = newLogradouro
                            viewModel.bairro = newBairro
                            viewModel.cidade = newCidade
                        },
                        addressRetrieved = "${viewModel.logradouro}"
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextInput(
                        value = logradouro,
                        onValueChange = { viewModel.logradouro = it },
                        label = "Logradouro",
                        placeholder = "Digite o logradouro (rua, avenida, número, etc.)",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = logradouroErro,
                        isRequired = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextInput(
                        value = bairro,
                        onValueChange = { viewModel.bairro = it },
                        label = "Bairro",
                        placeholder = "Digite o nome do bairro",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = bairroErro,
                        isRequired = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextInput(
                        value = complemento,
                        onValueChange = { viewModel.complemento = it },
                        label = "Complemento",
                        placeholder = "Digite o complemento (Apartamento, bloco)",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isRequired = false
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    CustomTextInput(
                        value = cidade,
                        onValueChange = { viewModel.cidade = it },
                        label = "Cidade",
                        placeholder = "Digite o nome da cidade",
                        modifier = Modifier.fillMaxWidth(),
                        isFormSubmitted = isFormSubmitted,
                        isError = cidadeErro,
                        isRequired = true
                    )


                    if (editarInfos.value) {
                        Spacer(modifier = Modifier.height(15.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    editarInfos.value = false
                                    isFormSubmitted = false

                                    clearForm()
                                    // Quando tiver conexão com o banco, pegar dados novamente
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
                                        editarInfos.value = false
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
                                editarInfos.value = true
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
