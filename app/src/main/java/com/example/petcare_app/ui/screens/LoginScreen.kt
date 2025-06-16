package com.example.petcare_app.ui.screens

import TokenDataStore
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.*
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.petcare_app.R
import com.example.petcare_app.data.network.RetrofitInstance
import com.example.petcare_app.data.network.RetrofitInstance.retrofit
import com.example.petcare_app.data.repository.LoginRepository
import com.example.petcare_app.data.services.LoginService
import com.example.petcare_app.data.viewmodel.LoginViewModel
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.components.layouts.LoadingBar
import com.example.petcare_app.ui.theme.montserratFontFamily
import org.koin.compose.koinInject

@Composable
fun LoginScreen(navController: NavController) {
    // Cores
    val primaryBlue = Color(0xFF005472)
    val lightYellow = Color(0xFFFFDC8A)

    val loginService: LoginService by lazy {
        retrofit.create(LoginService::class.java)
    }

    val context = LocalContext.current
    val dataStore: TokenDataStore = koinInject()
    val viewModel = remember {
        LoginViewModel(
            loginRepository = LoginRepository(loginService),
            dataStore = dataStore
        )
    }
    // Estados
    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var senhaVisivel by remember { mutableStateOf(false) }
    val loginResult by viewModel.loginState.collectAsState()
    var msgErro by remember { mutableStateOf("") }

    LaunchedEffect(loginResult) {
        if (!viewModel.isLoading) {
            loginResult?.let { result ->
                if (result.isSuccess) {
                    navController.navigate(Screen.HomeApp.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                } else if (result.isFailure) {
                    msgErro = "E-mail ou senha inválidos."
                }
            }
        }
    }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Espaço maior no topo para empurrar tudo para baixo
                    Spacer(modifier = Modifier.height(45.dp))

                    // Imagem de pets
                    Image(
                        painter = painterResource(id = R.drawable.pets_welcome),
                        contentDescription = "Cachorro e gato com balão de coração",
                        modifier = Modifier
                            .width(350.dp)
                            .height(290.dp),
                        contentScale = ContentScale.Fit
                    )

                    // Título
                    Text(
                        text = "Bem vindo(a)\nnovamente!",
                        color = primaryBlue,
                        fontSize = 37.sp,
                        fontFamily = FontFamily(Font(R.font.montserrat_extrabold)),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
                    )

                    // Espaço para ajustar o posicionamento
                    Spacer(modifier = Modifier.height(5.dp))

                    // Campo de e-mail
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "E-mail*",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontFamily = montserratFontFamily,
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            placeholder = { Text("Digite o seu e-mail", color = Color.LightGray) },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryBlue,
                                unfocusedBorderColor = Color.LightGray,
                                cursorColor = primaryBlue
                            ),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de senha
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = "Senha*",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.montserrat)),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        OutlinedTextField(
                            value = senha,
                            onValueChange = { senha = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            placeholder = { Text("Digite a sua senha", color = Color.LightGray) },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = primaryBlue,
                                unfocusedBorderColor = Color.LightGray,
                                cursorColor = primaryBlue
                            ),
                            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            trailingIcon = {
                                IconButton(onClick = { senhaVisivel = !senhaVisivel }) {
                                    Icon(
                                        imageVector = if (senhaVisivel) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                        contentDescription = "Toggle password visibility",
                                        tint = Color.Gray
                                    )
                                }
                            }
                        )
                        if (msgErro.isNotEmpty()) {
                            Text(
                                modifier = Modifier.padding(top = 4.dp),
                                text = msgErro,
                                color = Color.Red,
                                fontSize = 12.sp,
                                fontFamily = FontFamily(Font(R.font.montserrat))
                            )
                        }
                    }

                    // Link "Esqueci minha senha"
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            text = "Esqueci minha senha",
                            fontSize = 14.sp,
                            color = primaryBlue,
                            fontFamily = FontFamily(Font(R.font.montserrat)),
                            modifier = Modifier.clickable { /* Ação para redefinir senha */ }
                        )
                    }

                    // Espaço flexível maior antes do botão
                    Spacer(modifier = Modifier.weight(1f))

                    when {
                        viewModel.isLoading -> {
                            LoadingBar()
                        }

                        else -> {
                            // Botão Entrar
                            Button(
                                onClick = {
                                    msgErro = ""
                                    viewModel.login(email, senha)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(45.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = lightYellow,
                                    contentColor = primaryBlue
                                ),
                                shape = RoundedCornerShape(24.dp)
                            ) {
                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Entrar",
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = FontFamily(Font(R.font.montserrat)),
                                        modifier = Modifier.align(Alignment.Center)
                                    )

                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        contentDescription = "Entrar",
                                        modifier = Modifier
                                            .align(Alignment.CenterEnd)
                                            .padding(end = 8.dp)
                                            .size(24.dp)
                                    )
                                }
                            }
                        }
                    }


                    // Link para cadastro
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp, bottom = 32.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Não possui uma conta? ",
                            fontSize = 14.sp,
                            color = Color.Gray,
                            fontFamily = FontFamily(Font(R.font.montserrat))
                        )

                        Text(
                            text = "Registre-se",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            fontFamily = FontFamily(Font(R.font.montserrat)),
                            modifier = Modifier.clickable { navController.navigate(Screen.SignUpUser.route) }
                        )
                    }
                }
            }

}