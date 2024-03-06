package com.abdoali.mymidia3.ui.splashscreen

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.ui.MAIN_UI
import com.abdoali.mymidia3.ui.navToMainUi
import kotlinx.coroutines.delay


@Composable
fun Splashscreen(isLoading: Float, navController: NavController) {
    var showLogo = remember {
        MutableTransitionState<Boolean>(false)

    }
    val a = remember {
        MutableTransitionState<Boolean>(false)
    }
    val b = remember {
        MutableTransitionState<Boolean>(false)

    }
    val c = remember {
        MutableTransitionState<Boolean>(false)

    }
    val animation by animateFloatAsState(
        targetValue = isLoading,
        animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec,
        label = ""
    )

//    var k=false
    LaunchedEffect(true) {
        delay(300)
        showLogo.targetState = true
        delay(400)
        a.targetState = true
        delay(500)
        b.targetState = true
        delay(600)
        c.targetState = true
    }
    Column(
        verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally
    ) {

            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.displayLarge
                , modifier = Modifier.padding(16.dp)
            )
        

    AnimatedVisibility(visibleState = showLogo) {
        LinearProgressIndicator(
            progress = { animation }, modifier = Modifier.padding(8.dp)

        )
    }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
            , modifier = Modifier.padding(16.dp)

            ) {
            AnimatedVisibility(visibleState = a) {
                Text(text = "استمع")
            }
            Spacer(modifier = Modifier.width(10.dp))
            AnimatedVisibility(visibleState = b) {
                Text(text = "اقرأ")
            }
            Spacer(modifier = Modifier.width(10.dp))

            AnimatedVisibility(visibleState = c) {
                Text(text = "حمل")
            }

        }


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
    isLoading: Float,
) {
    composable(SPLASH, exitTransition = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Up, animationSpec = tween(2000)
        )
    }) {

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

//        LottieCompose()
    }
}