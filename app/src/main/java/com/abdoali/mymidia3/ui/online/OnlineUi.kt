package com.abdoali.mymidia3.ui.online

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.State.CREATED
import androidx.lifecycle.Lifecycle.State.DESTROYED
import androidx.lifecycle.Lifecycle.State.INITIALIZED
import androidx.lifecycle.Lifecycle.State.RESUMED
import androidx.lifecycle.Lifecycle.State.STARTED
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Reciter
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.ui.local.LOCALE
import com.abdoali.mymidia3.ui.online.log.navigateToLog
import com.abdoali.mymidia3.ui.online.search.navToSearch
import com.abdoali.mymidia3.uiCompount.lottie.LottieCompose

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun OnLineUI(

    navController: NavController,

    modifier: Modifier = Modifier,
    animationSpec: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()
    LaunchedEffect(key1 = lifecycleState) {
        Log.d("lifecycleState", "lifecycleState: $lifecycleState")
        when (lifecycleState) {

            DESTROYED -> {
            }
            INITIALIZED -> {}
            CREATED -> {}
            STARTED -> {}
            RESUMED -> {}
        }
    }

    val vmOnline: VMOnline = hiltViewModel()
    val artists by vmOnline.artists.collectAsState()
    val surah by vmOnline.surah.collectAsState()
    val favArtist by vmOnline.favArtist.collectAsState()
    val favSurah by vmOnline.favSurah.collectAsState()
    val favItem by vmOnline.favItem.collectAsState()
    if (artists.isEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
            LottieCompose()
            Text(text = stringResource(R.string.no_thing_to_show))
        }
    } else {

        OnLineUIImp(
            surah = surah,
            surahFav = favSurah,
            favArtist = favArtist,
            artists = artists,
            favItem = favItem,
            actionNavToArtistFav = navController::navToFavArtist,
            actionNavToListSurah = navController::navToSourList,
            actionNavToListArtists = navController::navToArtistList,
            actionNavToSurahOrArttist = navController::navToList,
            animationSpec = animationSpec,
            sharedTransitionScope = sharedTransitionScope,
            actionNavToFavSurahFav = navController::navToFavSourList,
            actionNavToItemFav = { navController.navToList("Fav", -1) },
            actionNavToLog = navController::navigateToLog,
            actionNavToSearch = navController::navToSearch,
            uiEvent = vmOnline::onUIEvent
        )
    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun OnLineUIImp(
    surah: List<String>,
    surahFav: List<String>,
    favArtist: List<Reciter>,
    favItem: List<QuranItem>,
    artists: List<Reciter>,
    actionNavToArtistFav: () -> Unit,
    actionNavToListSurah: () -> Unit,
    actionNavToListArtists: () -> Unit,
    actionNavToItemFav: () -> Unit,
    actionNavToLog: () -> Unit,
    actionNavToSearch: () -> Unit,
    actionNavToSurahOrArttist: (String, Int) -> Unit,
    uiEvent: (UIEvent) -> Unit,
    animationSpec: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
//    modifier: Modifier = Modifier,
    actionNavToFavSurahFav: () -> Unit,
) {
//    val scroll by rememberScrollState()

    with(sharedTransitionScope) {
        Column(

            Modifier
                .verticalScroll(rememberScrollState())
                .padding(paddingValues = PaddingValues(top = 8.dp))
        ) {
            Row {
                IconButton(
                    onClick = actionNavToSearch, modifier = Modifier.sharedBounds(
                        rememberSharedContentState(key = "searchUi"),
                        animatedVisibilityScope = animationSpec
                    )
                ) {
                    Icon(
                        Icons.Outlined.Search, contentDescription = null,
                        modifier = Modifier.sharedElement(
                            rememberSharedContentState(key = "search"),
                            animatedVisibilityScope = animationSpec
                        )
                    )
                }
                IconButton(
                    onClick = actionNavToLog, modifier = Modifier.sharedBounds(
                        rememberSharedContentState(key = "logUi"),
                        animatedVisibilityScope = animationSpec
                    )
                ) {
                    Icon(
                        Icons.Default.History, contentDescription = "log",
                        modifier = Modifier.sharedElement(
                            rememberSharedContentState(key = "log"),
                            animatedVisibilityScope = animationSpec
                        )
                    )


                }
            }


            MinListTitleItem(
                title = stringResource(R.string.favorite_item),
                list = favItem,
                onUIEvent = uiEvent,
                actionShowAll = actionNavToItemFav
            )
            if (surahFav.isNotEmpty()) {
                MinListTitle(
                    title = stringResource(R.string.favorite_surah),
                    titleSurh = surahFav,
                    actionNav = actionNavToSurahOrArttist,
                    actionShowAll = actionNavToFavSurahFav,
                    animationSpec = animationSpec,
                    sharedTransitionScope = sharedTransitionScope
                )
            }
            if (favArtist.isNotEmpty()) {
                MinListTitle(
                    title = stringResource(id = R.string.favorite_reciter),
                    titleReciter = favArtist,
                    animationSpec = animationSpec,
                    sharedTransitionScope = sharedTransitionScope,
                    actionNav = actionNavToSurahOrArttist,
                    actionShowAll = actionNavToArtistFav
                )
            }

            MinListTitle(
                title = stringResource(R.string.artist),
                titleReciter = artists,
                animationSpec = animationSpec,
                sharedTransitionScope = sharedTransitionScope,
                actionNav = actionNavToSurahOrArttist,
                actionShowAll = actionNavToListArtists
            )
            MinListTitle(
                title = stringResource(R.string.surah),
                titleSurh = surah,

                actionNav = actionNavToSurahOrArttist,
                actionShowAll = actionNavToListSurah,
                animationSpec = animationSpec,
                sharedTransitionScope = sharedTransitionScope
            )

        }
    }
//    LazyColumn {
//        item {
//            MinListTitleItem(
//                title = "favItems",
//                list = favItem,
//                onUIEvent = uiEvent,
//                actionShowAll = actionNavToItemFav
//            )
//        }
//        item {
//            if (surahFav.isNotEmpty()) {
//                MinListTitle(
//                    title = "faveroSurah",
//                    titleSurh = surahFav,
//                    actionNav = actionNavToSurahOrArttist,
//                    actionShowAll = actionNavToFavSurahFav
//                )
//            }
//        }
//        item {
//            if (favArtist.isNotEmpty()) MinListTitle(
//                title = "favor",
//
//                titleReciter = favArtist,
//                actionNav = actionNavToSurahOrArttist,
//                actionShowAll = actionNavToArtistFav
//            )
//        }
//        item {
//
//            MinListTitle(
//                title = stringResource(R.string.surah),
//                titleSurh = surah,
//
//                actionNav = actionNavToSurahOrArttist,
//                actionShowAll = actionNavToListSurah
//            )
//
//        }
//        item {
//            Column {
//                MinListTitle(
//                    title = stringResource(R.string.artist),
//                    titleReciter = artists,
//
//                    actionNav = actionNavToSurahOrArttist,
//                    actionShowAll = actionNavToListArtists
//                )
//
//            }
//
//        }
//
//    }
}
//
//@Composable
//fun ListPreview(
//    modifier: Modifier = Modifier
//) {
//    Box(
//        modifier = modifier.border(
//            BorderStroke(1.dp , Color.Yellow) ,
//            shape = MaterialTheme.shapes.small
//        )
//
//    ) {
//
//        Column(
//            horizontalAlignment = Alignment.CenterHorizontally ,
//            modifier = modifier.padding(4.dp)
//        )
//        {
//            Image(
//                painter = painterResource(id = R.drawable.logo2) ,
//                contentDescription = "fff" ,
//                contentScale = ContentScale.Fit
//
//            )
//
//            Text(text = "Title" , style = MaterialTheme.typography.titleLarge)
//            Text(text = "Title" , style = MaterialTheme.typography.titleSmall)
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ListPreviewPre() {
//    ListPreview()
//}

@Preview(showBackground = true)
@Composable
private fun OnLineUIPre() {
//    val list = listOf(
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
//    )
//    OnLineUIImp(
//        surah = listOf("one", "tow", "three", "four", "00000"),
//
//        surahFav = emptyList(),
//        favArtist = emptyList(),
//        artists = listOf(),
//        actionNavToArtistFav = {},
//        actionNavToListSurah = {},
//        actionNavToListArtists = {},
//        actionNavToSurahOrArtist = { s: String, i: Int -> },
//        actionNavToFavSurahFav = {},
//        favItem = emptyList(),
//        actionNavToItemFav = {},
//
//        uiEvent = {}
//    )
}

fun NavController.navToOnline() {
    navigate(ONLINE) {
        popUpTo(LOCALE) {
            inclusive = false
        }
        launchSingleTop = true
        restoreState = true
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
fun NavGraphBuilder.online(

    navController: NavController,
    sharedTransitionScope: SharedTransitionScope,

    ) {
    composable(ONLINE, enterTransition = {
        when (initialState.destination.route) {
            LOCALE -> slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(700)
            )

            else -> null
        }


    }, exitTransition = {
        when(targetState.destination.route){
            LOCALE -> slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(700)
            )
            else -> null
        }
    }, popEnterTransition = {
        when (targetState.destination.route) {
            LOCALE -> slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Start,
                animationSpec = tween(700)
            )

            else -> null
        }

    }, popExitTransition = {
        when(targetState.destination.route){
            LOCALE -> slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.End,
                animationSpec = tween(700)
            )
            else -> null
        }
    }
    ) {
        OnLineUI(

            navController = navController,
            animationSpec = this@composable,
            sharedTransitionScope = sharedTransitionScope
        )
    }
}

const val ONLINE = "ONLINE_ONLINE"

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}