package com.abdoali.mymidia3.ui.online

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.datasourece.QuranItem
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.ui.local.LOCALE

@Composable
fun OnLineUI(

    navController: NavController ,

    modifier: Modifier = Modifier
) {
    val vmOnline: VMOnline = hiltViewModel()
    val list by vmOnline.list.collectAsState()
    val artists by vmOnline.artists.collectAsState()
    val surah by vmOnline.soura.collectAsState()
    AnimatedVisibility(list.isEmpty()) {
        Text(text = stringResource(R.string.wait_a_minute))
    }
    AnimatedVisibility(visible = artists.size > 10) {

        OnLineUIImp(
            surah = surah ,

            artists = artists.map { it.name } ,

            actionNavToListSurah = { navController.navToSourList() } ,
            actionNavToListArtists = { navController.navToArtistList() } ,
            actionNavToSurahOrArttist = navController::navToList ,
            actionUi = vmOnline::onUIEvent
        )
    }

}

@Composable
fun OnLineUIImp(
    surah: List<String> ,

    artists: List<String> ,

    actionNavToListSurah: () -> Unit ,
    actionNavToListArtists: () -> Unit ,
    actionNavToSurahOrArttist: (String) -> Unit ,
    actionUi: (UIEvent) -> Unit ,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        item {
            Column {
                ListTitle(
                    title = stringResource(R.string.surah) ,
                    titleList = surah ,
                    actionNav = actionNavToSurahOrArttist ,
                    actionShowAll = actionNavToListSurah
                )

            }
        }
        item {
            Column {
                ListTitle(
                    title = stringResource(R.string.artist) ,
                    titleList = artists ,
                    actionNav = actionNavToSurahOrArttist ,
                    actionShowAll = actionNavToListArtists
                )

            }

        }

    }
}

@Composable
fun ListPreview(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.border(
            BorderStroke(1.dp , Color.Yellow) ,
            shape = MaterialTheme.shapes.small
        )

    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally ,
            modifier = modifier.padding(4.dp)
        )
        {
            Image(
                painter = painterResource(id = R.drawable.logo2) ,
                contentDescription = "fff" ,
                contentScale = ContentScale.Fit

            )

            Text(text = "Title" , style = MaterialTheme.typography.titleLarge)
            Text(text = "Title" , style = MaterialTheme.typography.titleSmall)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListPreviewPre() {
    ListPreview()
}

@Preview(showBackground = true)
@Composable
private fun OnLineUIPre() {
    val list = listOf(
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
    )
    OnLineUIImp(
        surah = listOf("one" , "tow" , "three" , "four" , "00000") ,

        artists = listOf("sssssone" , "tosssssssw" , "thressssssse" , "foursss") ,
        actionNavToListSurah = {} ,
        actionNavToSurahOrArttist = {} ,
        actionNavToListArtists = {} ,
        actionUi = {}

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

