package com.example.petcare_app.ui.components.layouts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.petcare_app.R
import com.example.petcare_app.ui.components.buttons.BackButton
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.sentenceTitleTextStyle


//@Preview
//@Composable
//private fun WhiteCanvasPrev() {
//    val navController = rememberNavController()
//    WhiteCanvas(modifier = Modifier, icon = ImageVector.vectorResource(R.drawable.ic_no_profile_picture), "Editar Perfil", true, {}, navController)}

@Composable
fun WhiteCanvas(
    modifier: Modifier,
    icon: ImageVector,
    title: String? = null,
    visibleBackButton: Boolean = false,
    actionIcon: () -> Unit = {},
    navController: NavController,
    content: @Composable () -> Unit = {},
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .padding(top = 20.dp, end = 20.dp, start = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Ícone",
                tint = Color(0, 84, 114),
                modifier = Modifier
                    .size(28.dp)
                    .clickable { actionIcon() }
            )

            Spacer(modifier = Modifier.width(8.dp))

            if (title != null) {
                Text(
                    text = title,
                    style = sentenceTitleTextStyle,
                    color = customColorScheme.primary,
                    modifier = Modifier.weight(1f) // Mantém o título centralizado sem ser empurrado para fora
                )
            }

            if (visibleBackButton) {
                Spacer(modifier = Modifier.width(8.dp)) // Espaçamento antes do botão
                Box(modifier = Modifier.width(90.dp)){
                    BackButton(navController = navController)
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        content()
    }
}
