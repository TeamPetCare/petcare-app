package com.example.petcare_app.ui.components.agendamentosComponents

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.R
import com.example.petcare_app.ui.components.layouts.StatusAgendamento
import com.example.petcare_app.ui.components.layouts.StatusComposable
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.montserratFontFamily
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle
import com.example.petcare_app.utils.DataUtils.formatarDataHora
import java.time.LocalDateTime

@Composable
fun NenhumAgendamentoCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(containerColor = customColorScheme.onSecondaryContainer),
        shape = RoundedCornerShape(8.dp)
    ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                        Image(
                            painter = painterResource(R.drawable.ic_noscheduleicon),
                            contentDescription = "Caderno com bulletpoints",
                            modifier = Modifier.size(80.dp)
                        )

                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.weight(1f).padding(start = 12.dp)
                    ) {
                        Text(
                            text = "A agenda está vazia!",
                            style = sentenceTitleTextStyle,
                            fontSize = 20.sp,
                            color = customColorScheme.primary
                        )
                        Text(
                            text = "Vamos marcar um dia de cuidados?",
                            style = sentenceTitleTextStyle,
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = customColorScheme.primary
                        )
                    }


                }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun NenhumAgendamentoCardPreview() {
   LazyColumn {
        item {
            NenhumAgendamentoCard()
        }
    }
}