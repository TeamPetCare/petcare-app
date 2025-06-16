package com.example.petcare_app.ui.components.bot

import TokenDataStore
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.SupervisedUserCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.petcare_app.R
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.montserratFontFamily
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

data class Question(
    val question: String,
    val answer: String
)
val questions = listOf(
    Question("🧼  Quais serviços estão disponíveis?",
        "Atualmente, oferecemos banho, tosa e hospedagem. Para agendar, clique no botão '+' no centro da tela."),

    Question("📅  Qual é o horário de funcionamento?",
        "Funcionamos de segunda a sexta-feira, das 9h às 18h, e aos sábados, das 9h às 13h. Não abrimos aos domingos."),

    Question("💬  Como vejo meus agendamentos?",
        "Acesse a aba 'Agendamentos' (ícone de relógio) no menu inferior para ver os horários e o status dos seus serviços. Também é possível realizar pagamentos pendentes por lá."),

    Question("🐶  Onde cadastro meu pet?",
        "Vá até o ícone de engrenagem, selecione 'Meus Pets' e clique em 'Adicionar novo pet'. Preencha as informações básicas como nome, raça e idade."),

    Question("💳  Quais formas de pagamento são aceitas?",
        "Aceitamos Pix, dinheiro, cartão de crédito e débito. O pagamento pode ser feito pelo app (via Pix) ou presencialmente na loja."),

    Question("🔄  Como cancelo um serviço?",
        "Entre na aba 'Agendamentos' (ícone de relógio), selecione o agendamento e clique no botão 'Cancelar agendamento'."),

    Question("✏️  Como remarco um agendamento?",
        "Acesse a aba 'Agendamentos', selecione o agendamento desejado e clique em 'Editar'. Escolha uma nova data e horário."),

    Question(
        "📞  Como entro em contato com a loja?",
        "Você pode nos chamar pelo WhatsApp: https://wa.me/5511947473755 ou https://wa.me/5511965081576"
    ),

    Question(
        "📍  Onde vocês estão localizados?",
        "Estamos na R. Otelo Augusto Ribeiro, 1072 - Guaianases, São Paulo - SP, 08412-000. Acesse: https://www.google.com/maps/search/?api=1&query=R.+Otelo+Augusto+Ribeiro,+1072"
    ),

    Question("👤  Como atualizo meus dados pessoais?",
        "Vá até o ícone de engrenagem, selecione 'Meu Perfil' e clique em 'Editar dados'."),

    Question("📲  O que fazer se o app estiver com erro?",
        "Tente fechar e abrir o app novamente. Se o problema persistir, envie uma mensagem pelo WhatsApp com uma captura de tela do erro.")
)


@Composable
fun HelpBot(
    onPopupToggle: () -> Unit,
    modifier: Modifier
) {
    val messages = remember { mutableStateListOf<Pair<String, Boolean>>() }
    var isTyping = remember { mutableStateOf(false) }

    val dataStore: TokenDataStore = koinInject()

    val userName by dataStore.getName.collectAsState(initial = "")

    LaunchedEffect(Unit) {
        delay(300)
        messages.add("Olá, $userName! 👋 Como posso te ajudar hoje?" to false)
    }

    Box(modifier = modifier) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .align(Alignment.BottomEnd)
                .padding(bottom = 65.dp),
            shape = RoundedCornerShape(15.dp),
            border = BorderStroke(2.dp, customColorScheme.primary),
            color = Color.White,
            tonalElevation = 4.dp
        ) {
            Column(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(customColorScheme.primary)
                        .padding(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_foto_mockado_perfil),
                                contentDescription = "Foto de perfil",
                                modifier = Modifier
                                    .size(45.dp)
                                    .padding(2.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Column {
                                Text("Lola", fontWeight = FontWeight.Bold, color = Color.White, fontFamily = montserratFontFamily)
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(10.dp)
                                            .background(Color.Green, shape = CircleShape)
                                    )
                                    Text("Online", color = Color.White, fontFamily = montserratFontFamily)
                                }
                            }
                        }

                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Fechar",
                            modifier = Modifier
                                .size(30.dp)
                                .clickable { onPopupToggle() },
                            tint = Color.White
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .background(Color.White)
                ) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(messages) { (message, isUser) ->
                            AnimatedVisibility(
                                visible = true,
                                enter = fadeIn(animationSpec = tween(500)) + slideInVertically(),
                                exit = fadeOut(animationSpec = tween(300))
                            ) {
                                if (message.contains("http")) {
                                    ClickableTextBubble(message, isMe = isUser)
                                } else {
                                    ChatBubble(message, isMe = isUser)
                                }
                            }
                        }

                        item {
                            AnimatedVisibility(visible = isTyping.value) {
                                TypingIndicator()
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(5.dp)
                        .drawBehind {
                            val strokeWidth = 1.dp.toPx()
                            drawLine(
                                color = customColorScheme.primary,
                                start = Offset(0f, 0f),
                                end = Offset(size.width, 0f),
                                strokeWidth = strokeWidth
                            )
                        }

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        LazyHorizontalGrid(
                            rows = GridCells.Fixed(2),
                            horizontalArrangement = Arrangement.Start,
                            modifier = Modifier.height(80.dp)
                        ) {
                            items(questions) { question ->
                                Button(
                                    onClick = {
                                        messages.add(question.question to true)

                                        CoroutineScope(Dispatchers.Main).launch {
                                            delay(500)
                                            isTyping.value = true
                                            delay(2000)
                                            isTyping.value = false
                                            messages.add(question.answer to false)
                                        }
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 5.dp, vertical = 2.dp)
                                        .clip(RoundedCornerShape(0.dp)),
                                    contentPadding = PaddingValues(vertical = 0.dp, horizontal = 8.dp)
                                ) {
                                    Text(
                                        text = question.question,
                                        color = Color.White,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp,
                                        fontFamily = montserratFontFamily
                                    )
                                }
                            }
                        }
                    }
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HelpBotPreview() {
    HelpBot(
        onPopupToggle = {},
        modifier = Modifier
    )
}