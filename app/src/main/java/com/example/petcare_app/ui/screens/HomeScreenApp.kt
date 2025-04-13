package com.example.petcare_app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WatchLater
import androidx.compose.material.icons.outlined.SupervisedUserCircle
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable
import com.example.petcare_app.ui.components.layouts.WhiteCanvas
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.paragraphTextStyle

@Composable
fun HomeScreenApp(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(topBar = {
            HeaderComposable(
                navController,
                userName = "Usuário"
            )
        }, bottomBar = { GadjetBarComposable(navController) }) { it ->
            Column(Modifier.background(Color(0, 84, 114)).padding(it)) {
                WhiteCanvas(
                    modifier = Modifier.fillMaxHeight(),
                    icon = Icons.Filled.WatchLater,
                    "Agendamentos do mês",
                    navController = navController
                ) {
                    Row {
                        Button(
                            onClick = { },
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Gray,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .padding(0.dp),
                            contentPadding = PaddingValues(vertical = 5.dp, horizontal = 0.dp)
                        ) {
                            Text(
                                style = paragraphTextStyle,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                text = "Todos os pets"
                            )
                        }
                    }

                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenAppPreview() {
    val navController = rememberNavController()

    HomeScreenApp(
        navController = navController
    )
}