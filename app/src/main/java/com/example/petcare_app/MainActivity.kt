package com.example.petcare_app

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.di.module.appModule
import com.example.petcare_app.navigation.NavGraph
import com.example.petcare_app.ui.theme.PetCareAppTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Solicita permissão para notificações (Android 13+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                    1001
                )
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "default_channel",
                "Canal padrão",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Canal para notificações padrão"
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }



        startKoin {
            modules(appModule)
            androidContext(this@MainActivity)
        }
        setContent {
            PetCareAppTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}
