package com.example.petcare_app.ui.components.plansComponents

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.ui.components.layouts.StatusComposable
import com.example.petcare_app.ui.theme.montserratFontFamily
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle

@Composable
fun PlanCard(plan: PlanItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
//            .height(100.dp)
            .padding(bottom = 8.dp),
        colors = CardDefaults.cardColors(containerColor = plan.backgroundColor),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(1.dp),
                ) {
                    Text(
                        text = plan.namePets,
                        fontSize = 14.sp,
                        color = plan.fontColor,
                        fontFamily = montserratFontFamily
                    )

                    Text(
                        text = plan.plan,
                        style = sentenceTitleTextStyle,
                        fontSize = 22.sp,
                        color = plan.fontColor
                    )

                    val icon = if (plan.status == "Ativo") Icons.Default.Check else Icons.Default.Close
                    Spacer(modifier = Modifier.height(3.dp))

                        StatusComposable(
                            icon = icon,
                            status = plan.status,
                            fontColor = if (plan.status == "Ativo") Color(0xFF2EC114) else Color(0xFFC11414),
                            backgroundColor = if (plan.status == "Ativo") Color(0xFF2EC114) else Color(0xFFC11414),
                            textColor = Color.White
                        )
                }
            }
        }
    }
}

@Preview
@Composable
fun PlanCardPreview() {

    val plans = listOf(
        PlanItem("Rex, Madonna", "Plano Mensal", "Ativo", Color(0,84,114),
            Color(255, 238, 200)),
        PlanItem("Madonna", "Plano Quinzenal", "Inativo Mudado", Color(0,84,114),
            Color(240,240,240)),
    )

    LazyColumn {
        items(plans) { plan ->
            PlanCard(plan)
        }
    }

}