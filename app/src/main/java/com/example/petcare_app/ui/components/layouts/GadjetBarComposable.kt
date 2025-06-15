package com.example.petcare_app.ui.components.layouts

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.SettingsSuggest
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.data.viewmodel.CreateScheduleViewModel
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.components.dialogs.createSchedule.CreateScheduleDialog
import com.example.petcare_app.ui.components.dialogs.createSchedule.CreateScheduleFlowDialog
import com.example.petcare_app.ui.theme.customColorScheme


@SuppressLint("NewApi")
@Composable
fun GadjetBarComposable(
    navController: NavController,
) {
    val openCreateScheduleDialog = remember { mutableStateOf(false) }
    val createScheduleViewModel: CreateScheduleViewModel = viewModel() // ← NOVO

    NavigationBar (
        containerColor = Color.White,
        contentColor = customColorScheme.primary,
        modifier = Modifier.padding(0.dp)
    ) {
        val items = listOf(
            "homeapp" to Pair(Icons.Outlined.Home, Icons.Filled.Home),
            "schedules" to Pair(Icons.Outlined.WatchLater, Icons.Filled.WatchLater),
            "plans" to Pair(Icons.Outlined.CalendarMonth, Icons.Filled.CalendarMonth),
            "settings" to Pair(Icons.Outlined.SettingsSuggest, Icons.Filled.SettingsSuggest),
        )
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.take(2).forEach { (route, icons) ->
            val icon = if(currentRoute == route) icons.second else icons.first

            NavigationBarItem(
                icon = {
                    Icon(
                        icon,
                        contentDescription = route,
                        tint = customColorScheme.primary,
                        modifier = Modifier
                            .size(30.dp)
                            .padding(0.dp)
                    )
                },
                selected = false,
                onClick = {
                    if (currentRoute != route) {
                        navController.navigate(route) {
                            popUpTo(Screen.HomeApp.route) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }

        NavigationBarItem(
            icon = {
                Box(
                    modifier = Modifier
                        .size(43.dp)
                        .background(
                            color = customColorScheme.primary,
                            shape = RoundedCornerShape(10.dp)
                        )
                        .padding(horizontal = 8.dp)
                ) {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = "Criar Agendamento",
                        tint = Color.White,
                        modifier = Modifier.size(43.dp)
                    )
                }
            },
            selected = false,
            onClick = {
                openCreateScheduleDialog.value = true
                createScheduleViewModel.hideScheduleFlow() // Reset do fluxo
            }
        )

        items.drop(2).forEach { (route, icons) ->
            val icon = if(currentRoute == route) icons.second else icons.first

            NavigationBarItem(
                icon = {
                    Icon(
                        icon,
                        contentDescription = route,
                        tint = customColorScheme.primary,
                        modifier = Modifier.size(30.dp)
                    )
                },
                selected = false,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(Screen.HomeApp.route) {
                            inclusive = false
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }

    // ← LÓGICA ATUALIZADA DOS DIALOGS
    // Fechar o dialog original quando o fluxo começar
    LaunchedEffect(createScheduleViewModel.shouldCloseOriginalDialog) {
        if (createScheduleViewModel.shouldCloseOriginalDialog) {
            openCreateScheduleDialog.value = false
        }
    }

    // Dialog original (formulário)
    if (openCreateScheduleDialog.value) {
        CreateScheduleDialog(
            onConfirm = { formData ->
                android.util.Log.d("GadjetBarComposable", "📨 onConfirm recebido! Iniciando fluxo...")
                createScheduleViewModel.startScheduleFlow(formData) // ← CONECTA COM O VIEWMODEL
            },
            setOpenCreateScheduleDialog = {
                openCreateScheduleDialog.value = it
            }
        )
    }

    // Novo fluxo (pagamento → confirmação → sucesso)
    if (createScheduleViewModel.showScheduleFlow) {
        CreateScheduleFlowDialog(
            viewModel = createScheduleViewModel,
            onDismiss = { createScheduleViewModel.hideScheduleFlow() }
        )
    }
}