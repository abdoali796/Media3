package com.abdoali.mymidia3.ui.search

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.mymidia3.ui.MAIN_UI

const val SEARCH = "SEARCHSEARCH"

fun NavGraphBuilder.search() {
    composable(SEARCH , enterTransition = {
        when (initialState.destination.route) {
            MAIN_UI -> slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Down ,
                animationSpec = tween(350)
            )

            else -> null
        }
    } , popExitTransition = {
        when (initialState.destination.route) {
            SEARCH -> slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Up ,
                animationSpec = tween(350)
            )

            else -> null
        }
    }
    ) {
        Search()

    }
}

fun NavController.navToSearch() {
    navigate(SEARCH) {
        popUpTo(MAIN_UI) {
            inclusive = false
        }
        launchSingleTop = true
        restoreState = true
    }
}