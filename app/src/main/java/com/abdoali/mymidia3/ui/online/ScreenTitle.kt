package com.abdoali.mymidia3.ui.online

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
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
//    val sour by vm.sura.collectAsState()
//    val id = remember {
//        vm.getID()
//    }
    Column(

        modifier = modifier
            .fillMaxSize()
            .border(BorderStroke(2.dp, Color.Black))
    ) {
        with(sharedTransitionScope) {
            LazyColumn {

                when (keys) {
                    SOUR_LIST -> {
                        items(items = vm.sura.value) {
                            Item(
                                main = it,
                                text2 = null,
                                animationSpec = animatedVisibilityScope,

                                modifier

                                    .clickable { navController.navToList(it, -1) })
                        }
                    }

                    SOUR_LIST_Fav -> {
                        items(items = vm.favSurah.value) {
                            Item(
                                main = it,
                                text2 = null,
                                animationSpec = animatedVisibilityScope,

                                modifier

                                    .clickable { navController.navToList(it, -1) })
                        }
                    }

                    ARTIST_LIST -> {
                        items(vm.artistsList.value) { reciter ->
                            reciter.moshaf.forEach {
                                Item(
                                    main = reciter.name,
                                    text2 = it.name,
                                    animationSpec = animatedVisibilityScope,

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

                    ARTIST_LIST_FAVOR -> {
                        items(vm.favArtist.value) { reciter ->
                            reciter.moshaf.forEach {
                                Item(
                                    main = reciter.name,
                                    text2 = it.name,
                                    animationSpec = animatedVisibilityScope,

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
    composable(SOUR_LIST_Fav ) {
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