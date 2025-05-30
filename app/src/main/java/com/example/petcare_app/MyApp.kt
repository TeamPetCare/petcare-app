package com.example.petcare_app

import android.R
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.petcare_app.di.module.appModule
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.installations.FirebaseInstallations
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)

//        FirebaseMessaging.getInstance().token
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val token = task.result
//                    Log.d("FCM", "Token: $token")
//                } else {
//                    Log.w("FCM", "Falha ao obter token", task.exception)
//                }
//            }


        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = "Firebase Token: $token"
            Log.d(TAG, msg)
        })

        FirebaseInstallations.getInstance().id
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val installationId = task.result
                    Log.d("FirebaseID", "Installation ID: $installationId")
                } else {
                    Log.e("FirebaseID", "Erro ao obter Installation ID", task.exception)
                }
                Log.e("Teste", "Isso foi iniciado")
            }


    }

}