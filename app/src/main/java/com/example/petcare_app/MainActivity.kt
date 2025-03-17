package com.example.petcare_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.navigation.NavGraph
import com.example.petcare_app.ui.theme.PetCareAppTheme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PetCareAppTheme {
                val navController = rememberNavController()
                NavGraph(navController)
            }
        }
    }
}
