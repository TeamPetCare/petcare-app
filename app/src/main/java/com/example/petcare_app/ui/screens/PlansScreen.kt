package com.example.petcare_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.data.viewmodel.PlanViewModel
import com.example.petcare_app.datastore.TokenDataStore
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable
import com.example.petcare_app.ui.components.layouts.LoadingBar
import com.example.petcare_app.ui.components.layouts.WhiteCanvas
import com.example.petcare_app.ui.components.plansComponents.PlanCard
import com.example.petcare_app.ui.components.plansComponents.PlanItem
import com.example.petcare_app.ui.theme.paragraphTextStyle
import com.example.petcare_app.R

@Composable
fun PlansScreen(navController: NavController) {
    val context = LocalContext.current
    val dataStore = TokenDataStore.getInstance(context)
    val viewModel: PlanViewModel = viewModel()

    val token by dataStore.getToken.collectAsState(initial = null)
    val id by dataStore.getId.collectAsState(initial = null)

    // Carregar dados quando token e id estiverem disponíveis
    LaunchedEffect(token, id) {
        if (token != null && id != null) {
            viewModel.getAllPlansByUserId(token!!, id!!)
        }
    }

    val plans by viewModel.allPlansUser.collectAsState()
    val error by viewModel.error.collectAsState()

    Scaffold(
        topBar = {
            HeaderComposable(navController)
        },
        bottomBar = { GadjetBarComposable(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .background(Color(0, 84, 114))
                .padding(paddingValues)
        ) {
            if (viewModel.isLoading) {
                LoadingBar()
            } else {
                WhiteCanvas(
                    modifier = Modifier.fillMaxHeight(),
                    icon = ImageVector.vectorResource(id = R.drawable.ic_plans),
                    title = "Planos",
                    navController = navController,
                    actionIcon = { navController.popBackStack() }
                ) {
                    when {
                        error != null -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Erro ao carregar planos: $error",
                                    style = paragraphTextStyle,
                                    color = Color.Red,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }
                        }

                        plans.isNotEmpty() -> {
                            LazyColumn {
                                items(plans) { plan ->
                                    val planItem = PlanItem(
                                        namePets = plan.petNames.joinToString(", "),
                                        plan = plan.planTypeName,
                                        status = if (plan.active) "Ativo" else "Inativo",
                                        fontColor = Color(0, 84, 114),
                                        backgroundColor = if (plan.active)
                                            Color(255, 238, 200)
                                        else
                                            Color(240, 240, 240)
                                    )
                                    PlanCard(planItem)
                                }
                            }
                        }

                        else -> {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "Nenhum plano encontrado",
                                    style = paragraphTextStyle,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(16.dp)
                                )
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
private fun PlansScreenPreview() {
    val navController = rememberNavController()
    PlansScreen(navController)
}