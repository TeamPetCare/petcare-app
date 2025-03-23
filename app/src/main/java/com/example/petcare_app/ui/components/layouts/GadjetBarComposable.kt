package com.example.petcare_app.ui.components.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SettingsSuggest
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.ui.theme.customColorScheme


@Composable
fun GadjetBarComposable(
    navController: NavController,
    criarAgendamento: () -> Unit
) {
    NavigationBar (
        containerColor = Color.White,
        contentColor = customColorScheme.primary,
        modifier = Modifier
            .padding(0.dp)
    ) {
        val items = listOf(
            "homeapp" to Pair(Icons.Outlined.Home, Icons.Filled.Home),
            "schedules" to Pair(Icons.Outlined.WatchLater, Icons.Filled.WatchLater),
            "plans" to Pair(Icons.Outlined.CalendarMonth, Icons.Filled.CalendarMonth),
            "settings" to Pair(Icons.Outlined.SettingsSuggest, Icons.Filled.SettingsSuggest),
        )
        val currentRoute = navController.currentBackStackEntry?.destination?.route

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
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
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
                        modifier = Modifier
                            .size(43.dp)
                    )
                }
            },
            selected = false,
            onClick = {
                criarAgendamento()
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
                        modifier = Modifier
                            .size(30.dp)
                    )
                },
                selected = false,
                onClick = {
                    navController.navigate(route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GadgetsPreview() {
    val navController = rememberNavController()

    GadjetBarComposable(
        navController = navController,
        criarAgendamento = {
        println("Criar Agendamento!")
    })
}
