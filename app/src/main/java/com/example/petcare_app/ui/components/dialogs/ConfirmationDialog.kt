package com.example.petcare_app.ui.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.montserratFontFamily

@Composable
fun ConfirmationDialog(
    title: String,
    nomeBotaoConfirmar: String = "Confirmar",
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        title = {
            Text(
                fontFamily = montserratFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = customColorScheme.primary,
                text = title
            )
        },
        confirmButton = {
            Button(
                onClick = {
                    onConfirm()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = customColorScheme.primary,
                    contentColor = Color.White
                )
            ) {
                Text(
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    text = nomeBotaoConfirmar
                )
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = customColorScheme.onSurface,
                    contentColor = Color.White
                ),
            ) {
                Text(
                    fontFamily = montserratFontFamily,
                    fontWeight = FontWeight.Bold,
                    text = "Cancelar"
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun ConfirmationDialogPreview() {
    ConfirmationDialog(
        title = "Título",
        nomeBotaoConfirmar = "Confirmar",
        onConfirm= {},
        onDismiss = {}
    )
}