package com.example.petcare_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable
import com.example.petcare_app.ui.components.layouts.WhiteCanvas
import com.example.petcare_app.ui.components.plansComponents.PlanCard
import com.example.petcare_app.ui.components.plansComponents.PlanItem
import com.example.petcare_app.R

@Composable
fun PlansScreen(navController: NavController) {

    val plans = listOf(
        PlanItem("Rex, Madonna", "Plano Mensal", "Ativo", Color(0,84,114),
            Color(255, 238, 200)),
        PlanItem("Madonna", "Plano Quinzenal", "Inativo", Color(0,84,114),
            Color(240,240,240)),
    )
    Scaffold(
        topBar = {
            HeaderComposable(
                navController,
                userName = "Usuário"
            )
        },
        bottomBar = { GadjetBarComposable(navController, criarAgendamento = {}) }
    ) {
        Column(Modifier.background(Color(0, 84, 114)).padding(it)) {
            WhiteCanvas(
                modifier = Modifier.fillMaxHeight(),
                icon = ImageVector.vectorResource(id = R.drawable.ic_plans),
                title = "Planos",
                navController = navController,
                actionIcon = { navController.popBackStack() }
            ) {
                LazyColumn {
                    items(plans) { plan ->
                        PlanCard(plan)
                    }
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PlansScreenPreview() {
    val navController = rememberNavController()
    PlansScreen(navController)
}