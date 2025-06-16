package com.example.petcare_app.ui.components.avaliacaoComponents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.petcare_app.data.viewmodel.SchedulesScreenViewModel
import com.example.petcare_app.ui.theme.buttonTextStyle
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.innerInputTextStyle
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AvaliacaoBottomSheet(
    showModal: Boolean,
    onDismiss: () -> Unit,
    onSubmit: (Int) -> Unit
) {
    if (showModal) {
        val sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = true
        )

        var selectedRating by remember { mutableStateOf(0) }

        ModalBottomSheet(
            onDismissRequest = {
                // Oculta o sheet (deslizar para baixo ou fora)
                onDismiss()
            },
            sheetState = sheetState,
            containerColor = Color.White,
            shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Como você avalia o atendimento e o serviço?",
                    textAlign = TextAlign.Center,
                    style = sentenceTitleTextStyle,
                    fontWeight = FontWeight.Bold,
                    fontSize = 25.sp,
                    color = customColorScheme.primary,
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = "Dê sua nota de 1 a 5 estrelas!",
                    textAlign = TextAlign.Center,
                    style = innerInputTextStyle,
                    fontSize = 17.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    (1..5).forEach { index ->
                        IconButton(onClick = { selectedRating = index }) {
                            Icon(
                                imageVector = Icons.Outlined.Star,
                                contentDescription = "Estrela $index",
                                modifier = Modifier.size(30.dp),
                                tint = if (index <= selectedRating) Color(0xFFFFC107) else Color.Gray
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        onSubmit(selectedRating)
                        onDismiss()
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
                            text = "Enviar Avaliação",
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
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun AvaliacaoBottomSheetPreview() {
//    AvaliacaoBottomSheet(
//        showModal = true,
//        onDismiss = {},
//        onSubmit = { nota ->
//            println("Avaliação enviada: $nota estrelas")
//        },
//        viewModel = viewModel()
//    )
//}