package com.abdoali.mymidia3.ui.online

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.helper.itSort
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.uiCompount.Item

@Composable
fun OnLineUI(
    list: List<QuranItem> ,
    surah: List<String> ,
    artists: List<String> ,
    navController: NavController ,
    onUIEvent: (UIEvent) -> Unit ,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(list.isEmpty()) {
        Text(text = "wita to get data")
    }
    AnimatedVisibility(visible = list.size > 10) {


        val random = remember {
            list.shuffled().subList(0 , 6)
        }
        val sort = remember {
            list.itSort().shuffled().subList(0 , 9)
        }
        OnLineUIImp(
            surah = surah ,
            random = random ,
            artists = artists ,
            sort = sort ,
            actionNavToListSurah = { navController.navToSourList() } ,
            actionNavToListArtists = { navController.navToArtistList() } ,
            actionNavToSurahOrArttist = navController::navToList ,
            actionUi = onUIEvent
        )
    }
}
@Composable
fun OnLineUIImp(
    surah: List<String> ,
    random: List<QuranItem> ,
    artists: List<String> ,
    sort :List<QuranItem>,
    actionNavToListSurah: () -> Unit ,
    actionNavToListArtists: () -> Unit ,
    actionNavToSurahOrArttist: (String) -> Unit ,
    actionUi: (UIEvent) -> Unit ,
    modifier: Modifier = Modifier
) {
    LazyColumn() {
        item {
            Column(
                modifier = modifier

                    .padding(5.dp)
                    .border(
                        2.dp ,
                        MaterialTheme.colorScheme.outline ,
                        MaterialTheme.shapes.extraSmall
                    )
                    .padding(5.dp)
            ) {
                Text(text = "surah")
                Item(
                    main = surah[4] ,
                    sacandery = null ,
                    modifier = modifier.clickable { actionNavToSurahOrArttist(surah[0]) })
                Item(
                    main = surah[1] ,
                    sacandery = null ,
                    modifier = modifier.clickable { actionNavToSurahOrArttist(surah[1]) })
                Item(
                    main = surah[2] ,
                    sacandery = null ,
                    modifier = modifier.clickable { actionNavToSurahOrArttist(surah[2]) })
                Item(
                    main = surah[3] ,
                    sacandery = null ,
                    modifier = modifier.clickable { actionNavToSurahOrArttist(surah[3]) })
                Text(
                    text = "all surah" ,
                    color = Color.Blue ,
                    style = MaterialTheme.typography.titleLarge ,
                    modifier = modifier.clickable { actionNavToListSurah() })
            }
        }
        item {
            Column {
                Text(text = "all artist")
                Item(
                    main = artists[0] ,
                    sacandery = null ,
                    modifier = modifier.clickable { actionNavToSurahOrArttist(artists[0]) })
                Item(
                    main = artists[1] ,
                    sacandery = null ,
                    modifier = modifier.clickable { actionNavToSurahOrArttist(artists[1]) })
                Item(
                    main = artists[2] ,
                    sacandery = null ,
                    modifier = modifier.clickable { actionNavToSurahOrArttist(artists[2]) })
                Item(
                    main = artists[3] ,
                    sacandery = null ,
                    modifier = modifier.clickable { actionNavToSurahOrArttist(artists[3]) })
                Text(
                    text = "all artists" ,
                    color = Color.Blue ,
                    style = MaterialTheme.typography.titleLarge ,
                    modifier = modifier.clickable { actionNavToListArtists() })
            }

        }
        item {
            Card {
                Text(text = "sort")
                Item(
                    main = sort[0].artist ,
                    sacandery = sort[0].surah ,
                    modifier = modifier.clickable { actionUi(UIEvent.SeekToIndex(sort[0].index)) })
                Item(
                    main = sort[1].artist ,
                    sacandery = sort[1].surah ,
                    modifier = modifier.clickable { actionUi(UIEvent.SeekToIndex(sort[1].index)) })
                Item(
                    main = sort[2].artist ,
                    sacandery = sort[2].surah ,
                    modifier = modifier.clickable { actionUi(UIEvent.SeekToIndex(sort[2].index)) })
                Item(
                    main = sort[3].artist ,
                    sacandery = sort[3].surah ,
                    modifier = modifier.clickable { actionUi(UIEvent.SeekToIndex(sort[3].index)) })
                Item(
                    main = sort[4].artist ,
                    sacandery = sort[4].surah ,
                    modifier = modifier.clickable { actionUi(UIEvent.SeekToIndex(sort[4].index)) })

                Item(
                    main = sort[5].artist ,
                    sacandery = sort[5].surah ,
                    modifier = modifier.clickable { actionUi(UIEvent.SeekToIndex(sort[5].index)) })
            }
        }
        item {
            Card {
                Text(text = "random")
                Item(
                    main = random[0].artist ,
                    sacandery = random[0].surah ,
                    modifier = modifier.clickable { actionUi(UIEvent.SeekToIndex(random[0].index)) })
                Item(
                    main = random[1].artist ,
                    sacandery = random[1].surah ,
                    modifier = modifier.clickable { actionUi(UIEvent.SeekToIndex(random[1].index)) })
                Item(
                    main = random[2].artist ,
                    sacandery = random[2].surah ,
                    modifier = modifier.clickable { actionUi(UIEvent.SeekToIndex(random[2].index)) })
                Item(
                    main = random[3].artist ,
                    sacandery = random[3].surah ,
                    modifier = modifier.clickable { actionUi(UIEvent.SeekToIndex(random[3].index)) })
                Item(
                    main = random[4].artist ,
                    sacandery = random[4].surah ,
                    modifier = modifier.clickable { actionUi(UIEvent.SeekToIndex(random[4].index)) })

                Item(
                    main = random[5].artist ,
                    sacandery = random[5].surah ,
                    modifier = modifier.clickable { actionUi(UIEvent.SeekToIndex(random[5].index)) })
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
fun OnLineUIPre() {
    val list=listOf(
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
        surah = listOf("one" , "tow" , "three" , "four","00000") ,
        random =  list,
        sort = list,
        artists = listOf("sssssone" , "tosssssssw" , "thressssssse" , "foursss") ,
        actionNavToListSurah = {} ,
        actionNavToSurahOrArttist = {} ,
        actionNavToListArtists = {} ,
        actionUi = {}

    )
}

fun NavController.navToOnline() {
navigate(ONLINE)
}

fun NavGraphBuilder.online(
    list: List<QuranItem> ,
    surah: List<String> ,
    artists: List<String> ,
    navController: NavController ,
    onUIEvent: (UIEvent) -> Unit
) {
    composable(ONLINE) {
        OnLineUI(list=list ,surah= surah ,artists= artists ,navController= navController , onUIEvent =  onUIEvent)
    }
}

 const val ONLINE = "ONLINE_ONLINE"

