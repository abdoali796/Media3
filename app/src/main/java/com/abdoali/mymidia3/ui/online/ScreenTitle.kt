package com.abdoali.mymidia3.ui.online

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.datasourece.api.Reciter
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.ui.local.navToLocale
import com.abdoali.mymidia3.uiCompount.Item

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ScreenTitle(
//    list: List<String>? ,
    keys: String,
    navController: NavController,
    modifier: Modifier = Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
//    reciter: List<Reciter> = emptyList() ,
) {
    val vm: VMList = hiltViewModel()
//    OnLifecycleEvent { owner, event ->
//        // do stuff on event
//        Log.d("OnLifecycleEvent", "OnLifecycleEvent: $owner")
//
////        when (event) {
////            Lifecycle.Event.ON_STOP -> {
////                vm.setDestroy(true)
////            }
////            else    -> { /* other stuff */ }
////        }
//    }

//    val sour by vm.sura.collectAsState()
//    val id = remember {
//        vm.getID()
//    }
    val state = vm.destroyed.collectAsState()

    LaunchedEffect(state) {
        Log.d("OnLifecycleEvent", "OnLifecycleEvent: ${state.value}")

        if (state.value) {
            Log.d("OnLifecycleEvent", "OnLifecycleEvcccccccccccccccccccccent: ${state.value}")

            navController.navToLocale()
            vm.setDestroy(false)
        }
    }

    Column(

        modifier = modifier
            .fillMaxSize()
            .border(BorderStroke(2.dp, Color.Black))
    ) {
        with(sharedTransitionScope) {




            when (keys) {
                SOUR_LIST -> {
                    ScreenTitleSurah(
                        list = vm.sura.value,
                        navController = navController,
                        scope = animatedVisibilityScope,
                        sharedTransitionScope = sharedTransitionScope,
                        title = stringResource(id = com.abdoali.mymidia3.R.string.surah)
                    )
//                        items(items = vm.sura.value) {
//                            Item(
//                                main = it,
//                                text2 = null,
//                                animationSpec = animatedVisibilityScope,
//
//                                modifier
//
//                                    .clickable { navController.navToList(it, -1) })
//                        }
                }

                SOUR_LIST_Fav -> {
                    ScreenTitleSurah(
                        list = vm.favSurah.value,
                        navController = navController,
                        scope = animatedVisibilityScope,
                        sharedTransitionScope = sharedTransitionScope,
                        title = stringResource(R.string.favorite_surah)
                    )
//                        items(items = vm.favSurah.value) {
//                            Item(
//                                main = it,
//                                text2 = null,
//                                animationSpec = animatedVisibilityScope,
//
//                                modifier
//
//                                    .clickable { navController.navToList(it, -1) })
//                        }
                }

                ARTIST_LIST -> {
                    ScreenTitleArtist(
                        list = vm.artistsList.value,
                        navController = navController,
                        scope = animatedVisibilityScope,
                        sharedTransitionScope = sharedTransitionScope,
                        title = stringResource(id = R.string.artist)

                    )
//                        items(vm.artistsList.value) { reciter ->
//                            reciter.moshaf.forEach {
//                                Item(
//                                    main = reciter.name,
//                                    text2 = it.name,
//                                    animationSpec = animatedVisibilityScope,
//
//                                    modifier
//
//                                        .clickable {
//                                            navController.navToList(
//                                                title = reciter.name
//                                                        + "," + it.name, id = reciter.id
//                                            )
//
//                                        })
//                            }
                }


                ARTIST_LIST_FAVOR -> {
                    ScreenTitleArtist(
                        list = vm.favArtist.value,
                        navController = navController,
                        scope = animatedVisibilityScope,
                        sharedTransitionScope = sharedTransitionScope,
                        title = stringResource(R.string.favorite_reciter)
                    )
                }
            }

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun ScreenTitleSurah(
    list: List<String>,
    navController: NavController,
    scope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    title: String,
    modifier: Modifier = Modifier,
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    with(sharedTransitionScope) {


        Scaffold(modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .sharedBounds(
                rememberSharedContentState(key = title + "A"), animatedVisibilityScope = scope
            ), topBar = {
            MediumTopAppBar(title = {
                Text(
                    text = title,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier.sharedElement(
                        rememberSharedContentState(key = title),
                        scope
                    )
                )


            }, navigationIcon = {

            }, actions = {

            }, scrollBehavior = scrollBehavior)
        }) { paddingValues ->
            LazyColumn(Modifier.padding(paddingValues)) {
                items(list) {
                    Item(
                        main = it,
                        text2 = null,
                        animationSpec = scope,

                        modifier

                            .clickable { navController.navToList(it, -1) })

                }
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun ScreenTitleArtist(
    list: List<Reciter>,
    navController: NavController,
    scope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope,
    title: String,
    modifier: Modifier = Modifier,
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    with(sharedTransitionScope) {


        Scaffold(modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .sharedBounds(
                rememberSharedContentState(key = title + "A"), animatedVisibilityScope = scope

            ), topBar = {
            MediumTopAppBar(title = {
                Text(
                    text = title,
                    overflow = TextOverflow.Clip,
                    modifier = Modifier.sharedElement(
                        rememberSharedContentState(key = title),
                        scope
                    )
                )


            }, navigationIcon = {

            }, actions = {

            }, scrollBehavior = scrollBehavior)
        }) { paddingValues ->
            LazyColumn(Modifier.padding(paddingValues)) {
                items(list) { reciter ->
                    reciter.moshaf.forEach {
                        Item(
                            main = reciter.name,
                            text2 = it.name,
                            animationSpec = scope,

                            modifier
                                .clickable {
                                    navController.navToList(
                                        title = reciter.name
                                                + "," + it.name, id = reciter.id
                                    )

                                })

                    }

                }
            }

        }
    }
}

fun NavController.navToSourList() {
    navigate(SOUR_LIST)
}

fun NavController.navToFavSourList() {
    navigate(SOUR_LIST_Fav)
}

fun NavController.navToArtistList() {
    navigate(ARTIST_LIST)
}

fun NavController.navToFavArtist() {
    navigate(ARTIST_LIST_FAVOR)
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.sourList(
    navController: NavController,

    sharedTransitionScope: SharedTransitionScope,

    ) {
    composable(SOUR_LIST) {
        ScreenTitle(
            SOUR_LIST,
            navController,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = this@composable
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.sourFavList(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
) {
    composable(SOUR_LIST_Fav) {
        ScreenTitle(
            SOUR_LIST_Fav, navController,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = this@composable
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.artistFavList(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
) {
    composable(ARTIST_LIST_FAVOR) {
        ScreenTitle(
            ARTIST_LIST_FAVOR,
            navController,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = this@composable
        )
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.artistList(
    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,
) {
    composable(ARTIST_LIST) {
        ScreenTitle(
            ARTIST_LIST,
            navController,
            sharedTransitionScope = sharedTransitionScope,
            animatedVisibilityScope = this@composable
        )
    }
}

const val SOUR_LIST = "SOUR_LISTSOUR_LIST"
const val SOUR_LIST_Fav = "SOUR_UR_LIST_Fav"
const val ARTIST_LIST = "ARTIST_LIST"
const val ARTIST_LIST_FAVOR = "ARTIST_LIST_FAVORIT"
