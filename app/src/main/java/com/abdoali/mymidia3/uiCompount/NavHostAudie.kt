package com.abdoali.mymidia3.uiCompount

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.ui.local.LOCALE
import com.abdoali.mymidia3.ui.local.locale
import com.abdoali.mymidia3.ui.online.ONLINE
import com.abdoali.mymidia3.ui.online.artistFavList
import com.abdoali.mymidia3.ui.online.artistList
import com.abdoali.mymidia3.ui.online.list
import com.abdoali.mymidia3.ui.online.online
import com.abdoali.mymidia3.ui.online.sourFavList
import com.abdoali.mymidia3.ui.online.sourList

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavHostAudie(
    navController: NavHostController,
    uiEvent: (UIEvent) -> Unit,
    modifier: Modifier = Modifier,

    ) {
    SharedTransitionLayout {


        NavHost(
            navController = navController, startDestination = ONLINE, modifier = modifier
        ) {
            list(onUIEvent = uiEvent, sharedTransitionScope = this@SharedTransitionLayout)
            locale()
            online(navController = navController)
            sourFavList(
                navController = navController, sharedTransitionScope = this@SharedTransitionLayout
            )
            sourList(
                navController = navController, sharedTransitionScope = this@SharedTransitionLayout,
            )
            artistList(
                navController = navController, sharedTransitionScope = this@SharedTransitionLayout
            )
            artistFavList(navController = navController, sharedTransitionScope = this@SharedTransitionLayout)

        }
    }
}

fun getIndexDestination(string: String?): Int {
    if (string == null) return 0
    if (string == LOCALE) return 0
    return 1
}