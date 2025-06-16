package com.example.petcare_app.ui.components.bot

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.petcare_app.ui.theme.customColorScheme
import com.example.petcare_app.ui.theme.montserratFontFamily
import androidx.compose.ui.text.withStyle

@Composable
fun ChatBubble(message: String, isMe: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (isMe) customColorScheme.secondary else customColorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .wrapContentWidth()
        ) {
            Text(
                text = message,
                fontWeight = FontWeight.Medium,
                fontFamily = montserratFontFamily,
                fontSize = 14.sp,
                color = if (isMe) customColorScheme.primary else Color.White,
            )
        }
    }
}

@Composable
fun ClickableTextBubble(message: String, isMe: Boolean = false) {
    val uriHandler = LocalUriHandler.current

    val annotatedString = buildAnnotatedString {
        val words = message.split(" ")
        for (word in words) {
            if (word.startsWith("http")) {
                pushStringAnnotation(tag = "URL", annotation = word)
                withStyle(
                    style = SpanStyle(
                        color = Color.Yellow,
                        fontWeight = FontWeight.Bold,
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append("$word ")
                }
                pop()
            } else {
                append("$word ")
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        horizontalArrangement = if (isMe) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(
                    if (isMe) customColorScheme.secondary else customColorScheme.primary,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(8.dp)
                .wrapContentWidth()
        ) {
            ClickableText(
                text = annotatedString,
                onClick = { offset ->
                    annotatedString.getStringAnnotations("URL", offset, offset)
                        .firstOrNull()?.let {
                            uriHandler.openUri(it.item)
                        }
                },
                style = TextStyle(
                    fontWeight = FontWeight.Medium,
                    fontFamily = montserratFontFamily,
                    fontSize = 14.sp,
                    color = if (isMe) customColorScheme.primary else Color.White
                )
            )
        }
    }
}