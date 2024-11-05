package com.example.missingyou.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Tema principal de la aplicación MissingYou.
 *
 * @param darkTheme Indica si se debe utilizar el tema oscuro. Por defecto, sigue el tema del sistema.
 * @param content Contenido a renderizar dentro del tema.
 */
@Composable
fun MissingYouTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // Configura el esquema de color basado en el tema
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    // Obtiene la vista actual
    val view = LocalView.current

    // Verifica si la vista no está en modo edición
    if (!view.isInEditMode) {
        // Efecto secundario para configurar la barra de estado y las barras de navegación
        SideEffect {
            // Obtiene la ventana actual
            val window = (view.context as Activity).window

            // Configura el color de la barra de estado
            window.statusBarColor = colorScheme.primary.toArgb()

            // Configura el controlador de inserciones
            val insetsController = WindowCompat.getInsetsController(window, view)
            insetsController.isAppearanceLightStatusBars = !darkTheme
        }
    }

    // Aplica el tema material
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Asegúrate de definir 'Typography' en tu archivo de tema
        content = content
    )
}