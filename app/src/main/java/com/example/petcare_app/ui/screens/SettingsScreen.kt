package com.example.petcare_app.ui.screens

import TokenDataStore
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.SettingsSuggest
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.R
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.components.buttons.LogoutButton
import com.example.petcare_app.ui.components.layouts.LoadingBar
import com.example.petcare_app.ui.components.layouts.WhiteCanvas
import com.example.petcare_app.ui.theme.customColorScheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.compose.koinInject

@Composable
fun SettingsScreen(navController: NavController) {
    val context = LocalContext.current
    val tokenDataStore: TokenDataStore = koinInject()

    Scaffold(
        topBar = { HeaderComposable(navController) },
        bottomBar = { GadjetBarComposable(navController) }
    ) { it ->
        Column(Modifier.background(Color(0, 84, 114)).padding(it)) {
            WhiteCanvas(
                modifier = Modifier.fillMaxHeight(),
                icon = Icons.Filled.SettingsSuggest,
                "Agendamentos",
                navController = navController
            ) {
                // Editar Perfil
                Row(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clickable {
                            navController.navigate(Screen.EditUser.route)
                        },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Ícone da conta",
                        modifier = Modifier.size(24.dp),
                        tint = customColorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Editar Perfil",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = customColorScheme.primary,
                        fontFamily = FontFamily(Font(R.font.montserrat))
                    )
                }

                // Editar Pets
                Row(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clickable {
                            navController.navigate(Screen.EditUser.route)
                        },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Pets,
                        contentDescription = "Ícone da pata",
                        modifier = Modifier.size(24.dp),
                        tint = customColorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Editar Meus Pets",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = customColorScheme.primary,
                        fontFamily = FontFamily(Font(R.font.montserrat))
                    )
                }

//            Spacer(modifier = Modifier.height(16.dp))

                // Botão de Sair
                LogoutButton(navController, tokenDataStore)
            }
        }
    }
}

@Preview(showBackground = true) 
@Composable
fun SettingsScreenPreview() {
    val navController = rememberNavController()
    SettingsScreen(
        navController
    )
}
