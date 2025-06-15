package com.example.petcare_app.ui.components.buttons

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.petcare_app.R
import com.example.petcare_app.datastore.TokenDataStore
import com.example.petcare_app.navigation.Screen
import com.example.petcare_app.ui.components.dialogs.ConfirmationDialog
import com.example.petcare_app.ui.components.layouts.LoadingBar
import com.example.petcare_app.ui.theme.customColorScheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LogoutButton(navController: NavController, tokenDataStore: TokenDataStore) {
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    // Componente de confirmação de logout
    if (showConfirmationDialog) {
        ConfirmationDialog(
            title = "Você tem certeza que deseja sair?",
            onConfirm = {
                isLoading = true
                CoroutineScope(Dispatchers.IO).launch {
                    tokenDataStore.clearUserInfo()
                    withContext(Dispatchers.Main) {
                        isLoading = false
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.startDestinationId) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                }
            },
            onDismiss = { showConfirmationDialog = false }
        )
    }

    // Exibindo o botão de logout
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                showConfirmationDialog = true
            },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.Logout,
            contentDescription = "Sair",
            modifier = Modifier.size(24.dp),
            tint = customColorScheme.primary
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Sair",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = customColorScheme.primary,
            fontFamily = FontFamily(Font(R.font.montserrat))
        )
    }

    if (isLoading) {
        LoadingBar()
    }
}
