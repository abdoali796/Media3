package com.abdoali.mymidia3.uiCompount

import android.util.Log
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle.State.CREATED
import androidx.lifecycle.Lifecycle.State.DESTROYED
import androidx.lifecycle.Lifecycle.State.INITIALIZED
import androidx.lifecycle.Lifecycle.State.RESUMED
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.ui.local.LOCALE
import com.abdoali.mymidia3.ui.local.locale
import com.abdoali.mymidia3.ui.online.artistFavList
import com.abdoali.mymidia3.ui.online.artistList
import com.abdoali.mymidia3.ui.online.list
import com.abdoali.mymidia3.ui.online.log.logUi
import com.abdoali.mymidia3.ui.online.navToOnline
import com.abdoali.mymidia3.ui.online.online
import com.abdoali.mymidia3.ui.online.search.search
import com.abdoali.mymidia3.ui.online.sourFavList
import com.abdoali.mymidia3.ui.online.sourList

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun NavHostAudie(
    navController: NavHostController,
    uiEvent: (UIEvent) -> Unit,
    sharedTransitionScope :SharedTransitionScope,
    modifier: Modifier = Modifier,

    ) {

        NavHost(
            navController = navController, startDestination = LOCALE, modifier = modifier
        ) {
            list(onUIEvent = uiEvent, sharedTransitionScope = sharedTransitionScope)
            locale()
            online(navController = navController, sharedTransitionScope = sharedTransitionScope)
            sourFavList(
                navController = navController, sharedTransitionScope = sharedTransitionScope
            )
            sourList(
                navController = navController, sharedTransitionScope = sharedTransitionScope,
            )
            artistList(
                navController = navController, sharedTransitionScope = sharedTransitionScope
          )
            artistFavList(
                navController = navController,
                sharedTransitionScope = sharedTransitionScope
            )
            logUi(sharedTransitionScope = sharedTransitionScope)
            search(
                navController = navController,
                sharedTransitionScope = sharedTransitionScope
            )


        }


}
fun getIndexDestination(string: String?)=  if (string == null || string == LOCALE)  0 else 1
