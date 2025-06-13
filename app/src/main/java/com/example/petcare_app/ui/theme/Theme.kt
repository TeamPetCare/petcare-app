package com.example.petcare_app.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import com.example.petcare_app.R

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40
)

val customColorScheme = lightColorScheme(
    primary = DarkBlue,
    surface = DarkGray,
    onSurface = LightGray,
    onSurfaceVariant = LighterGray,
    secondary = Yellow,
    secondaryContainer = LightYellowDisabled,
    onSecondaryContainer = LightYellow,
    error = errorMessage,
)

val customColorStatusAgendamentoScheme = lightColorScheme(
    primary = DarkBlueStatusSchedule,
    secondary = DarkGreenStatusSchedule,
    tertiary = LightGrayStatusSchedule,
    onPrimary = LightBlueStatusSchedule,
    onSecondary = LightGreenStatusSchedule,
    onTertiary = LightGray
)

// Montserrat Font Family
val montserratFontFamily = FontFamily(
    Font(R.font.montserrat_black, weight = FontWeight.Black),
    Font(R.font.montserrat_black_italic, weight = FontWeight.Black, style = FontStyle.Italic),
    Font(R.font.montserrat_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.montserrat_extrabold_italic, weight = FontWeight.ExtraBold, style = FontStyle.Italic),
    Font(R.font.montserrat_bold, weight = FontWeight.Bold),
    Font(R.font.montserrat_bold_italic, weight = FontWeight.Bold, style = FontStyle.Italic),
    Font(R.font.montserrat_semibold, weight = FontWeight.SemiBold),
    Font(R.font.montserrat_semibold_italic, weight = FontWeight.SemiBold, style = FontStyle.Italic),
    Font(R.font.montserrat_medium, weight = FontWeight.Medium),
    Font(R.font.montserrat_medium_italic, weight = FontWeight.Medium, style = FontStyle.Italic),
    Font(R.font.montserrat_regular, weight = FontWeight.Normal),
    Font(R.font.montserrat_italic, weight = FontWeight.Normal, style = FontStyle.Italic),
    Font(R.font.montserrat_light, weight = FontWeight.Light),
    Font(R.font.montserrat_light_italic, weight = FontWeight.Light, style = FontStyle.Italic),
    Font(R.font.montserrat_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.montserrat_extralight_italic, weight = FontWeight.ExtraLight, style = FontStyle.Italic),
    Font(R.font.montserrat_thin, weight = FontWeight.Thin),
    Font(R.font.montserrat_thin_italic, weight = FontWeight.Thin, style = FontStyle.Italic)
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

val alertDialogText = TextStyle(
    fontFamily = montserratFontFamily,
    fontWeight = FontWeight.Normal,
    fontSize = 15.sp,
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
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    // Passando a estilização de texto diretamente, sem usar o Typography
    MaterialTheme(
        colorScheme = customColorScheme,
        content = content
    )
}
