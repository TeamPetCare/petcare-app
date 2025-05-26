package com.example.petcare_app.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.R
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.montserratFontFamily

@Composable
fun RegisteredPetsScreen(navController: NavController) {
    // Dados mockados para demonstração
    val mockPets = listOf(
        PetData(
            id = 1,
            name = "Thor",
            breed = "Cachorro - Labrador",
            photoRes = R.drawable.pets_welcome // Usando imagem do projeto
        ),
        PetData(
            id = 2,
            name = "Madona",
            breed = "Cachorro - Labrador",
            photoRes = R.drawable.pet_hand_high_five // Usando imagem do projeto
        )
    )

    Scaffold(
        topBar = {
            HeaderComposable(navController)
        },
        bottomBar = {
            GadjetBarComposable(navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF005472))
                .padding(paddingValues)
        ) {
            // Conteúdo principal com fundo branco arredondado
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 0.dp),
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    // Header sem ícone, apenas título e botão voltar
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.patinha),
                            contentDescription = "Pets",
                            modifier = Modifier.size(24.dp)
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = "Pets Cadastrados",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = customColorScheme.primary,
                            fontFamily = montserratFontFamily,
                            modifier = Modifier.weight(1f)
                        )

                        TextButton(
                            onClick = { navController.popBackStack() },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Voltar",
                                    tint = customColorScheme.primary,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Voltar",
                                    color = customColorScheme.primary,
                                    fontFamily = montserratFontFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Lista de pets
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        items(mockPets) { pet ->
                            PetListItem(
                                pet = pet,
                                onEditClick = {
                                    // Navegar para tela de edição
                                },
                                onDeleteClick = {
                                    // Ação de deletar pet
                                }
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Botão Adicionar Pet
                    Button(
                        onClick = {
                            // Navegar para tela de cadastro de pet
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = customColorScheme.primary
                        ),
                        shape = RoundedCornerShape(24.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            customColorScheme.primary
                        )
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "Adicionar Pet",
                                fontSize = 16.sp,
                                fontFamily = montserratFontFamily,
                                fontWeight = FontWeight.Medium
                            )
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Adicionar",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(24.dp)
                                    .background(
                                        color = customColorScheme.primary,
                                        shape = CircleShape
                                    )
                                    .padding(4.dp)
                                    .align(Alignment.CenterEnd)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PetListItem(
    pet: PetData,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Foto do pet
        Image(
            painter = painterResource(id = pet.photoRes),
            contentDescription = "Foto do ${pet.name}",
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        // Nome e raça
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = pet.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = customColorScheme.primary,
                fontFamily = montserratFontFamily
            )
            Text(
                text = pet.breed,
                fontSize = 14.sp,
                color = customColorScheme.primary,
                fontFamily = montserratFontFamily
            )
        }

        // Ícones de ação
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.lixo),
                contentDescription = "Deletar",
                modifier = Modifier.size(20.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.lapis),
                contentDescription = "Editar",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

// Modelo de dados
data class PetData(
    val id: Int,
    val name: String,
    val breed: String,
    val photoRes: Int
)

@Preview(showBackground = true)
@Composable
fun RegisteredPetsScreenPreview() {
    val navController = rememberNavController()
    RegisteredPetsScreen(navController)
}