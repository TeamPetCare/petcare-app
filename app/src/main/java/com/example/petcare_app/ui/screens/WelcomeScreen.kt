package com.example.petcare_app.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.petcare_app.R
import com.example.petcare_app.data.viewmodel.CpfValidationViewModel
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.components.formFields.inputFields.MaskedInput
import com.example.petcare_app.ui.components.formFields.inputFields.isValidCPF
import com.example.petcare_app.ui.components.layouts.LoadingBar
import com.example.petcare_app.ui.theme.customColorScheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WelcomeScreen(navController: NavController) {
    val primaryBlue = Color(0xFF005472)
    val context = LocalContext.current
    var isFormSubmitted by remember { mutableStateOf(false) }

    // Estado do CPF
    var cpf by remember { mutableStateOf("") }
    val viewModel: CpfValidationViewModel = viewModel()
    val cpfResult by viewModel.cpfValidate.collectAsState()
    var cpfErro by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    fun validateCpf() {
        viewModel.validateCpf(cpf = cpf)
    }

    fun validateInput(): Boolean {
        cpfErro = cpf.isEmpty() || !isValidCPF(cpf)
        return !(cpfErro)
    }

    LaunchedEffect(cpfResult) {
        cpfResult?.let { result ->
            result.onSuccess { isValid ->
                if (isValid) {
                    isFormSubmitted = false
                    navController.navigate(Screen.Login.route) {
                        launchSingleTop = true
                    }
                } else {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "Não encontramos o seu CPF no sistema, vamos te redirecionar para a tela de registro.",
                            duration = SnackbarDuration.Short,
                        )
                    }
                    delay(2000)
                    navController.navigate(Screen.SignUpUser.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            }
            result.onFailure {
                snackbarHostState.showSnackbar(
                    message = "Erro interno ao validar CPF. Reinicie o aplicativo.",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

//    DisposableEffect(Unit) {
//        onDispose {
//            // Resetar o estado apenas se a navegação não foi acionada
//            if (!navigationTriggered) {
//                cpf = ""  // Limpa o CPF
//                cpfErro = false  // Limpa o erro
//                navigationTriggered = false  // Permite a navegação novamente
//            }
//        }
//    }

    if (viewModel.isLoading) {
        LoadingBar()
    } else {
        Scaffold(
            snackbarHost = {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = { data ->
                        Snackbar(
                            contentColor = Color.White,
                            containerColor = customColorScheme.primary,
                            snackbarData = data,
                        )
                    }
                )
            },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Imagem de pets
                        Image(
                            painter = painterResource(id = R.drawable.pets_welcome),
                            contentDescription = "Cachorro e gato com balão de coração",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        // Título principal
                        Text(
                            text = "Bem-vindo(a)!",
                            color = primaryBlue,
                            fontSize = 32.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat_extrabold)),
                            textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Subtítulo
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Queremos garantir que é você mesmo!\nInsira seu CPF para te identificarmos ",
                                color = primaryBlue,
                                fontSize = 14.sp,
                                fontFamily = FontFamily(Font(R.font.montserrat)),
                                textAlign = TextAlign.Center
                            )

                            // Emoji
                            Text(
                                text = "",
                                fontSize = 10.sp
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        MaskedInput(
                            label = "CPF",
                            value = cpf,
                            onValueChange = {
                                if (it.length <= 14) cpf = it
                            },
                            placeholder = "___.___.___-__",
                            type = "CPF",
                            modifier = Modifier.fillMaxWidth(),
                            isFormSubmitted = isFormSubmitted,
                            isError = cpfErro
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Botão Verificar
                        Button(
                            onClick = {
                                if (validateInput()) {
                                    isFormSubmitted = false
                                    viewModel.setIsLoading(true)
                                    validateCpf()
                                } else isFormSubmitted = true
                            },
                            colors = buttonColors(
                                containerColor = if (cpfErro && isFormSubmitted) customColorScheme.error else customColorScheme.secondary,
                                contentColor = if (cpfErro && isFormSubmitted) Color.White else customColorScheme.primary,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(24.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "Verificar",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(Font(R.font.montserrat)),
                                    modifier = Modifier.align(Alignment.Center)
                                )

                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = "Próximo",
                                    modifier = Modifier
                                        .align(Alignment.CenterEnd)
                                        .padding(end = 8.dp)
                                        .size(24.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Texto informativo
                        Text(
                            text = "Seu CPF é usado apenas para\nlocalizar sua conta. Não se preocupe,\nseus dados estão seguros.",
                            color = primaryBlue,
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.montserrat_medium)),
                            textAlign = TextAlign.Center,
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        )
    }
}

