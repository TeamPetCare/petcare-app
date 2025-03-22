package com.example.petcare_app.ui.theme

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import com.example.petcare_app.R
import java.io.File

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

val customColorScheme = lightColorScheme(
    primary = DarkBlue,
    surface = DarkGray,
    onSurface = LightGrey,
    secondary = LightYellow,
    secondaryContainer = LightYellowDisabled,
    error = errorMessage,

)

val montserratFontFamily = FontFamily(
    Font(R.font.montserrat_regular, weight = FontWeight.Normal),  // Regular
    Font(R.font.montserrat_bold, weight = FontWeight.Bold),     // Bold
    Font(R.font.montserrat_black, weight = FontWeight.Black),    // Black
    Font(R.font.montserrat_extrabold, weight = FontWeight.ExtraBold),// ExtraBold
    Font(R.font.montserrat_extralight, weight = FontWeight.ExtraLight),// ExtraLight
    Font(R.font.montserrat_light, weight = FontWeight.Light),    // Light
    Font(R.font.montserrat_medium, weight = FontWeight.Medium),   // Medium
    Font(R.font.montserrat_semibold, weight = FontWeight.SemiBold), // SemiBold
    Font(R.font.montserrat_thin, weight = FontWeight.Thin),     // Thin
    Font(R.font.montserrat_black_italic, weight = FontWeight.Black, style = FontStyle.Italic), // Black Italic
    Font(R.font.montserrat_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),  // Bold Italic
    Font(R.font.montserrat_extrabold_italic, weight = FontWeight.ExtraBold, style = FontStyle.Italic), // ExtraBold Italic
    Font(R.font.montserrat_extralight_italic, weight = FontWeight.ExtraLight, style = FontStyle.Italic), // ExtraLight Italic
    Font(R.font.montserrat_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),  // Light Italic
    Font(R.font.montserrat_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic), // Medium Italic
    Font(R.font.montserrat_semibold_italic, weight = FontWeight.SemiBold, style = FontStyle.Italic), // SemiBold Italic
    Font(R.font.montserrat_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic)    // Thin Italic
)

// TextStyles

val titleTextStyle = TextStyle(
    fontFamily = montserratFontFamily,
    fontWeight = FontWeight.ExtraBold,
    fontSize = 36.sp,
    lineHeight = 31.sp
)

val sentenceTitleTextStyle = TextStyle(
    fontFamily = montserratFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 20.sp
)

val paragraphTextStyle = TextStyle(
    fontFamily = montserratFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 17.sp,
    lineHeight = 17.sp
)

val labelInputTextStyle = TextStyle(
    fontFamily = montserratFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 12.sp,
    color = customColorScheme.surface
)

val innerInputTextStyle = TextStyle(
    fontFamily = montserratFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 14.sp,
    color = customColorScheme.onSurface
)

val buttonTextStyle = TextStyle(
    fontFamily = montserratFontFamily,
    fontWeight = FontWeight.Bold,
    fontSize = 17.sp
)

val errorTextStyle = TextStyle(
    fontFamily = montserratFontFamily,
    fontWeight = FontWeight.Medium,
    fontSize = 13.sp,
    color = customColorScheme.error
)


@Composable
fun PetCareAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Passando a estilização de texto diretamente, sem usar o Typography
    MaterialTheme(
        colorScheme = customColorScheme,
        content = content
    )
}
