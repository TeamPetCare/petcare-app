package com.example.petcare_app.ui.components.signUpPetScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.data.viewmodel.Pet
import com.example.petcare_app.ui.theme.buttonTextStyle
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.paragraphTextStyle
import com.example.petcare_app.ui.theme.titleTextStyle

@Composable
fun AddPet(
    pets: List<Pet>,
    isPetFormActive: (Boolean) -> Unit,
    resetForm: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                style = titleTextStyle,
                fontSize = 20.sp,
                color = customColorScheme.primary,
                text = "Pets cadastrados:"
            )
        }
        Spacer(modifier = Modifier.height(5.dp))

        pets.forEachIndexed { index, pet ->

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Text(
                        style = paragraphTextStyle,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        text = "${pet.nome}"
                    )
                    Text(
                        style = paragraphTextStyle,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = customColorScheme.primary,
                        text = "${pet.raca}"
                    )
                }

                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = Icons.Filled.Delete,
                    tint = customColorScheme.error,
                    contentDescription = "Ícone de Lixeira"
                )

                Icon(
                    modifier = Modifier.size(25.dp),
                    imageVector = Icons.Filled.Edit,
                    tint = customColorScheme.surface,
                    contentDescription = "Ícone de Lápis"
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
        Spacer(modifier = Modifier.height(10.dp))

        // Botão "Adicionar pet"
        Button(
            onClick = {
                resetForm()
                isPetFormActive(true)
            },
            modifier = Modifier
                .border(1.dp, customColorScheme.primary, RoundedCornerShape(30.dp))
                .fillMaxWidth()
                .size(40.dp),
            colors = buttonColors(
                containerColor = Color.Transparent,
                contentColor = customColorScheme.primary
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = buttonTextStyle,
                    text = "Adicionar pet"
                )

                Box(
                    modifier = Modifier
                        .size(24.dp)
                        .background(customColorScheme.primary, shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Adicionar",
                        tint = Color.White,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }

        // Espaço entre os componentes
        Spacer(modifier = Modifier.weight(1f))

        // Botão "Finalizar cadastro"
        Button(
            onClick = {
                // Enviar dados para o banco
            },
            colors = buttonColors(
                containerColor = customColorScheme.secondary,
                contentColor = customColorScheme.primary,
            ),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Finalizar cadastro",
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