package com.example.petcare_app.ui.components.agendamentosComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pix
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.example.petcare_app.ui.theme.buttonTextStyle
import com.example.petcare_app.ui.theme.customColorScheme
import android.content.Intent
import android.net.Uri
import android.widget.Toast

@Composable
fun LinkPagamento(
    linkPagamentoFunction: () -> String?,
    context: android.content.Context
) {
    Button(
        onClick = {
            val url = linkPagamentoFunction()
            if (!url.isNullOrEmpty()) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                context.startActivity(intent)
            } else {
                Toast.makeText(context, "Link de pagamento indisponível", Toast.LENGTH_SHORT).show()
            }
        },
        colors = buttonColors(
            containerColor = customColorScheme.secondary,
            contentColor = customColorScheme.primary,
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.Pix,
                tint = customColorScheme.primary,
                contentDescription = "Icone Pix"
            )
            Text(
                text = "Finalizar Pagamento via Pix",
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                style = buttonTextStyle
            )
        }
    }
}
