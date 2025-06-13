package com.example.petcare_app.ui.screens

import TokenDataStore
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.R
import com.example.petcare_app.data.viewmodel.NotificationViewModel
import com.example.petcare_app.ui.components.layouts.GadjetBarComposable
import com.example.petcare_app.ui.components.layouts.HeaderComposable
import com.example.petcare_app.ui.components.layouts.WhiteCanvas
import com.example.petcare_app.ui.components.notificationComponents.NotificationCard
import com.example.petcare_app.ui.components.notificationComponents.NotificationItem
import com.example.petcare_app.ui.theme.customColorScheme
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject


//@Preview
//@Composable
//private fun NotificationScreenPreview() {
//    val navController = rememberNavController()
//    val notificationViewModel = koinViewModel<NotificationViewModel>()
//    NotificationScren(navController, notificationViewModel)
//}

@Composable
fun NotificationScren(navController: NavController, notificationViewModel: NotificationViewModel) {

    val dataStore: TokenDataStore = koinInject()

    val notifications = notificationViewModel.notificationItemList

    val token by dataStore.getToken.collectAsState(initial = null)
    val id by dataStore.getId.collectAsState(initial = null)

    Text("Token: ${token ?: "null"}")
    Text("ID: ${id ?: "null"}")

    LaunchedEffect(token, id) {
        if (!token.isNullOrBlank() && id != null) {
            Log.d("EditUserScreen", "Token: $token | ID: $id")
            notificationViewModel.getAllUserNotificationsById(token!!, id!!)
        } else {
            Log.w("EditUserScreen", "Token ou ID nulo: token=$token, id=$id")
        }
    }

    Scaffold(
        topBar = {
            HeaderComposable(navController)
        },
        bottomBar = { GadjetBarComposable(navController) }
    ) { it ->
        Column(
            Modifier
                .background(Color(0, 84, 114))
                .padding(it)
        ) {
            WhiteCanvas(
                modifier = Modifier.fillMaxHeight(),
                icon = Icons.Filled.Close,
                title = "Notificações",
                visibleBackButton = false,
                actionIcon = { navController.popBackStack() },
                navController = navController,
                content = {
                    LazyColumn {
                        items(notifications) { notification ->
                            NotificationCard(notification)
                        }
                    }
                     Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = {
                                    if(notificationViewModel.page.value - 1 >= 0){
                                        notificationViewModel.page.value--;
                                        notificationViewModel.getAllUserNotificationsById(token!!, id!!)
                                    }
                                },
                                colors = buttonColors(
                                    containerColor = if (notificationViewModel.page.value > 0) customColorScheme.primary else Color(109, 124, 132),
                                    contentColor = Color.White,
                                )
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(0.4f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                        contentDescription = "Seta para a esquerda"
                                    )
                                    Text(
                                        text = "Página anterior",
                                        modifier = Modifier,
                                        textAlign = TextAlign.Center,
                                    )
                                }
                            }
                            Button(
                                onClick = {
                                    if(notificationViewModel.notificationItemList.size == 7){
                                        notificationViewModel.page.value++;
                                        notificationViewModel.getAllUserNotificationsById(token!!, id!!)
                                    }
                                },
                                colors = buttonColors(
                                    containerColor = if (notificationViewModel.notificationItemList.size == 7) customColorScheme.primary else Color(109, 124, 132),
                                    contentColor = Color.White,
                                )
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(0.8f),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = "Próxima página",
                                        modifier = Modifier,
                                        textAlign = TextAlign.Center,
                                    )
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        contentDescription = "Seta para a direita"
                                    )
                                }
                            }
                        }
                }
            )
        }

    }
}
