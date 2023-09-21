package com.abdoali.mymidia3.ui.theme

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.abdoali.mymidia3.ui.settings.Theme
import com.abdoali.mymidia3.ui.theme.color.BlueDarkColors
import com.abdoali.mymidia3.ui.theme.color.BlueLightColors
import com.abdoali.mymidia3.ui.theme.color.RedDarkColors
import com.abdoali.mymidia3.ui.theme.color.RedLightColors
import com.abdoali.mymidia3.ui.theme.color.brownDarkcolorscheme
import com.abdoali.mymidia3.ui.theme.color.brownLightColorScheme


@Composable
fun Mymidia3Theme(
    darkTheme: Boolean = isSystemInDarkTheme() ,
    // Dynamic color is available on Android 12+
//    dynamicColor: Boolean = false ,
    colorTheme: Theme ,
    content: @Composable () -> Unit
) {
    Log.i("changeTheme" , "theme $colorTheme")

    val colorScheme =
        when (colorTheme) {
            Theme.Blue -> {
                Log.i("changeTheme" , "theme blue")

                if (darkTheme) BlueDarkColors else BlueLightColors

            }

            Theme.Brown -> {
                Log.i("changeTheme" , "theme brown")
                if (darkTheme) brownDarkcolorscheme else brownLightColorScheme
            }
            Theme.Red-> {
                if (darkTheme) RedDarkColors else RedLightColors

            }


        }
    val view = LocalView.current
    if (! view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window , view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme ,
        typography = Typography ,
        content = content
    )
}