package com.example.notesapp.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = CopperRose,
    onPrimary = OnCopperRose,
    primaryContainer = CopperRoseContainer,
    onPrimaryContainer = CopperRoseDark,
    secondary = DustyRose,
    onSecondary = OnDustyRose,
    secondaryContainer = DustyRoseContainer,
    onSecondaryContainer = DustyRoseDark,
    tertiary = Rosewater,
    onTertiary = OnRosewater,
    tertiaryContainer = RosewaterContainer,
    onTertiaryContainer = RosewaterDark,
    background = ChinaDollLight,
    onBackground = TextPrimary,
    surface = ChinaDollSurface,
    onSurface = TextPrimary,
    surfaceVariant = RosewaterLight,
    onSurfaceVariant = TextSecondary,
    error = CopperRose,
    onError = OnCopperRose,
    errorContainer = CopperRoseLight,
    outline = ChinaDollDark,
    outlineVariant = PlumWineContainer
)

@Composable
fun NotesAppTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = ChinaDollLight.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
