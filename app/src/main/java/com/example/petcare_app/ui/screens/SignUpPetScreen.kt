package com.example.petcare_app.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.petcare_app.navigation.SignUpViewModel
import com.example.petcare_app.ui.components.buttons.BackButton

@Composable
fun SignUpPetScreen(navController: NavController, viewModel: SignUpViewModel) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(start = 20.dp, bottom = 30.dp, top = 15.dp, end = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        BackButton(navController = navController)
        Spacer(modifier = Modifier.height(20.dp))
    }
}