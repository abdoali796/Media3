package com.abdoali.mymidia3.ui.splashscreen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.ui.MAIN_UI
import com.abdoali.mymidia3.ui.navToMainUi
import com.abdoali.mymidia3.uiCompount.lottie.LottieCompose
import kotlinx.coroutines.delay


@Composable
fun Splashscreen(isLoading: Float, navController: NavController) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineLarge
        )
        Text(text = isLoading.toString())
        val animation by animateFloatAsState(
            targetValue = isLoading,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )

        LinearProgressIndicator(
            progress = { animation },
        )
    }
    LaunchedEffect(key1 = isLoading) {
        Log.i("isLoading", "isLoadingForMain$isLoading")

        if (isLoading == 1f) {
            delay(500)
            navController.navToMainUi()
        }
    }
}

const val SPLASH = "SPLASH_SPLASH"

fun NavGraphBuilder.splash(
    subNavController: NavController,
    isLoading: Float
) {
    composable(SPLASH

        , exitTransition = {
        slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(2000))
    }
    ) {

        Splashscreen(navController = subNavController, isLoading = isLoading)

    }
}

fun NavController.navToSplash() {
    navigate(SPLASH) {
        popUpTo(MAIN_UI) {
            inclusive = false
        }
        launchSingleTop = true
        restoreState = false
    }
}

@Preview(
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_NO or Configuration.UI_MODE_TYPE_UNDEFINED
)
@Composable
fun Sp() {
    Column(
        verticalArrangement = Arrangement.Bottom, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineLarge
        )

        LottieCompose()
    }
}