package com.example.petcare_app.ui.screens


import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
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
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.navigation.SignUpViewModel
import com.example.petcare_app.ui.components.buttons.BackButton
import com.example.petcare_app.ui.components.inputFields.CepInput
import com.example.petcare_app.ui.components.inputFields.CustomTextInput
import com.example.petcare_app.ui.components.inputFields.EmailInput
import com.example.petcare_app.ui.components.inputFields.MaskedInput
import com.example.petcare_app.ui.components.inputFields.PasswordInput
import com.example.petcare_app.ui.components.inputFields.isValidCPF
import com.example.petcare_app.ui.theme.buttonTextStyle
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.paragraphTextStyle
import com.example.petcare_app.ui.theme.titleTextStyle

//@Composable
//fun SignupScreenPreview() {
//    SignupScreen()
//}

@Composable
fun SignUpUserScreen(navController: NavController, viewModel: SignUpViewModel) {
    val nomeCompleto = viewModel.nomeCompleto
    val cpf = viewModel.cpf
    val email = viewModel.email
    val celular = viewModel.celular
    val senha = viewModel.senha
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
    var confirmarSenhaErro by remember { mutableStateOf(false) }
    var cepErro by remember { mutableStateOf(false) }
    var logradouroErro by remember { mutableStateOf(false) }
    var bairroErro by remember { mutableStateOf(false) }
    var cidadeErro by remember { mutableStateOf(false) }

//  Função de validação
    fun validateForm(): Boolean {
        nomeErro = nomeCompleto.length < 5
        cpfErro = cpf.isEmpty() || !isValidCPF(cpf)
        emailErro = email.isEmpty() || !email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$".toRegex())
        celularErro = celular.isEmpty() || !celular.matches("^\\(?\\d{2}\\)?\\s?9\\d{4}-?\\d{4}\$".toRegex())
        senhaErro = senha.isEmpty() || !senha.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}\$".toRegex())
        confirmarSenhaErro = confirmarSenha.isEmpty() || confirmarSenha != senha
        cepErro = cep.length < 8
        logradouroErro = logradouro.length < 5
        bairroErro = bairro.length < 5
        cidadeErro = cidade.length < 5

        return !(nomeErro || cpfErro || emailErro || celularErro || senhaErro || confirmarSenhaErro || cepErro || logradouroErro || bairroErro || cidadeErro)
    }

//  Função para enviar os dados para o banco
    fun sendData() {
        Log.d("FORM_SIGNUP", "Dados: " +
                " Nome - $nomeCompleto" +
                " CPF - $cpf" +
                " E-mail - $email" +
                " Celular - $celular" +
                " Senha - $senha" +
                " Confirmar Senha - $confirmarSenha" +
                " CEP - $cep" +
                " Logradouro - $logradouro" +
                " Bairro - $bairro" +
                " Complemento - $complemento" +
                " Cidade - $cidade")
    }

//  Tela de Inscrição - Sobre o Usuário
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 20.dp, bottom = 30.dp, top = 15.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BackButton(navController = navController)
        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Compartilhe um pouco sobre você!",
            style = titleTextStyle,
            color = customColorScheme.primary,
            textAlign = TextAlign.Center
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
            label = "Senha",
            value = senha,
            onValueChange = { viewModel.senha = it },
            placeholder = "Crie uma senha",
            modifier = Modifier.fillMaxWidth(),
            isFormSubmitted = isFormSubmitted,
            isError = senhaErro,
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

//      Botão de envio
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = {
                if (validateForm()) {
                    sendData()
                    navController.navigate(Screen.SignUpPet.route)
                } else isFormSubmitted = true
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
    }
}


//@Preview(showBackground = true)
//@Composable
//fun signupPreview() {
//    SignupScreenPreview()
//}
