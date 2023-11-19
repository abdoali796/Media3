package com.abdoali.mymidia3.ui.online

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Reciter
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.ui.local.LOCALE

@Composable
fun OnLineUI(

    navController: NavController ,

    modifier: Modifier = Modifier
) {
    val vmOnline: VMOnline = hiltViewModel()
//    val list by vmOnline.list.collectAsState()
    val artists by vmOnline.artists.collectAsState()
    val surah by vmOnline.surah.collectAsState()
    val favArtist by vmOnline.favArtist.collectAsState()
    val favSurah by vmOnline.favSurah.collectAsState()
    val favItem by vmOnline.favItem.collectAsState()
    AnimatedVisibility(artists.isEmpty()) {
        Text(text = stringResource(R.string.wait_a_minute))
    }
    AnimatedVisibility(visible = artists.size > 10) {

        OnLineUIImp(
            surah = surah ,
            surahFav = favSurah ,
            favArtist = favArtist ,
            artists = artists ,
            favItem = favItem ,
            actionNavToArtistFav = navController::navToFavArtist ,
            actionNavToListSurah = navController::navToSourList ,
            actionNavToListArtists = navController::navToArtistList ,
            actionNavToSurahOrArttist = navController::navToList ,
            actionNavToFavSurahFav = navController::navToFavSourList ,
            actionNavToItemFav = { navController.navToList("Fav" , - 1) } ,
            uiEvent = vmOnline::onUIEvent
        )
    }

}

@Composable
fun OnLineUIImp(
    surah: List<String> ,
    surahFav: List<String> ,
    favArtist: List<Reciter> ,
    favItem: List<QuranItem> ,
    artists: List<Reciter> ,
    actionNavToArtistFav: () -> Unit ,
    actionNavToListSurah: () -> Unit ,
    actionNavToListArtists: () -> Unit ,
    actionNavToItemFav: () -> Unit ,
    actionNavToSurahOrArttist: (String , Int) -> Unit ,
    uiEvent: (UIEvent) -> Unit ,
    modifier: Modifier = Modifier ,
    actionNavToFavSurahFav: () -> Unit
) {

    LazyColumn {
        item {
            MinListTitleItem(
                title = "favItems" ,
                list = favItem ,
                onUIEvent = uiEvent ,
                actionShowAll = actionNavToItemFav
            )
        }
        item {
            if (surahFav.isNotEmpty()) {
                MinListTitle(
                    title = "faveroSurah" ,
                    titleSurh = surahFav ,
                    actionNav = actionNavToSurahOrArttist ,
                    actionShowAll = actionNavToFavSurahFav
                )
            }
        }
        item {
            if (favArtist.isNotEmpty()) MinListTitle(
                title = "favor" ,

                titleReciter = favArtist ,
                actionNav = actionNavToSurahOrArttist ,
                actionShowAll = actionNavToArtistFav
            )
        }
        item {

            MinListTitle(
                title = stringResource(R.string.surah) ,
                titleSurh = surah ,

                actionNav = actionNavToSurahOrArttist ,
                actionShowAll = actionNavToListSurah
            )

        }
        item {
            Column {
                MinListTitle(
                    title = stringResource(R.string.artist) ,
                    titleReciter = artists ,

                    actionNav = actionNavToSurahOrArttist ,
                    actionShowAll = actionNavToListArtists
                )

            }

        }

    }
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
    OnLineUIImp(
        surah = listOf("one" , "tow" , "three" , "four" , "00000") ,

        surahFav = emptyList() ,
        favArtist = emptyList() ,
        artists = listOf() ,
        actionNavToArtistFav = {} ,
        actionNavToListSurah = {} ,
        actionNavToListArtists = {} ,
        actionNavToSurahOrArttist = { s: String , i: Int -> } ,
        actionNavToFavSurahFav = {} ,
        favItem = emptyList() ,
        actionNavToItemFav = {} ,

        uiEvent = {}
    )
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

fun NavGraphBuilder.online(

    navController: NavController ,

    ) {
    composable(ONLINE) {
        OnLineUI(

            navController = navController
        )
    }
}

const val ONLINE = "ONLINE_ONLINE"

