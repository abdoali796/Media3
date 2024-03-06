package com.abdoali.mymidia3.ui.search

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.mymidia3.ui.MAIN_UI

const val SEARCH = "SEARCHSEARCH"

fun NavGraphBuilder.search(
    subNavController: NavController,
    mainNavController: NavController
) {
    composable(SEARCH , enterTransition = {
        when (initialState.destination.route) {
//            MAIN_UI -> slideIntoContainer(
//                AnimatedContentTransitionScope.SlideDirection.Down ,
//                animationSpec = tween(350)
//            )
MAIN_UI-> expandVertically (expandFrom = Alignment.CenterVertically){
20
}
            else -> null
        }
    } , exitTransition = {
        when (initialState.destination.route) {
            SEARCH -> shrinkVertically (tween(1000)){fullHeight ->
                fullHeight / 2
            }

            else -> null
        }
    }
    ) {
        Search(subNavController = subNavController ,mainNavController=mainNavController)

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