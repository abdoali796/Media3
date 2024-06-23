package com.abdoali.mymidia3.ui

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.abdoali.datasourece.QuranItem
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.uiCompount.Item

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun ListMp(
    quranItem: List<QuranItem>,
    title: String?,
    id: Int?,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedContentScope,
    favorAddAction: (Int, String) -> Unit,
    favorDelAction: (Int, String) -> Unit,
    uiEvent: (UIEvent) -> Unit,
    modifier: Modifier = Modifier,
    itFav: Boolean = false,
) {
    val list = remember {
        quranItem.map {
            it.index
        }
    }
    with(sharedTransitionScope) {
        val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
        Scaffold(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
            MediumTopAppBar(title = {
                if (title != null) {
                    Title(
                        title = title,
                        id = id,
                        animatedVisibilityScope = animatedVisibilityScope,
                        moshaf = quranItem[0].moshaf
                    )
                }
            }, navigationIcon = {

            }, actions = {
                if (id != null) {
                    if (id == 0) return@MediumTopAppBar
                    FilledIconButton(onClick = {
                        //id =-1 it surah list
                        if (id != -1) {
                            if (!itFav) {
                                favorAddAction(id, "")
                            } else {
                                favorDelAction(id, "")
                            }

                        } else {
                            if (!itFav) {
                                if (title != null) {
                                    favorAddAction(-1, title)
                                }
                            } else {
                                if (title != null) {
                                    favorDelAction(-1, title)
                                }
                            }
                        }
                    }) {
                        if (itFav) Icon(
                            Icons.Default.Favorite, contentDescription = null
                        )
                        else Icon(
                            Icons.Outlined.FavoriteBorder, contentDescription = null
                        )

                    }
                }
                FilledIconButton(onClick = { uiEvent(UIEvent.SetPlayList(list)) }) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                }
            }, scrollBehavior = scrollBehavior)
        }) { padding ->

            Column(

                modifier = modifier
                    .padding(padding)
                    .fillMaxSize()
//            .border(BorderStroke(2.dp , Color.Black))
            ) {
                LazyColumn {
//            item {
//                Button(onClick = { uiEvent(UIEvent.SetPlayList(list)) }) {
//                    Text(text = "تشغل القائمة كاملة")
//                }
//            }
                    items(items = quranItem, key = { i -> i.index }) {
                        Item(main = it.surah,
                            text2 = it.artist,
                            text3 = it.moshaf,
                            modifier = Modifier.clickable { uiEvent(UIEvent.SeekToIndex(it.index)) })
                    }
                    item {
                        Spacer(modifier = modifier.height(30.dp))
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListMp(
    quranItem: List<QuranItem>,
    title: String?,
    id: Int?,

    favorAddAction: (Int, String) -> Unit,
    favorDelAction: (Int, String) -> Unit,
    uiEvent: (UIEvent) -> Unit,
    modifier: Modifier = Modifier,
    itFav: Boolean = false,
) {
    val list = remember {
        quranItem.map {
            it.index
        }
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection), topBar = {
        MediumTopAppBar(title = {
            if (title != null) {
                Text(
                    text = title,
                    overflow = TextOverflow.Clip,
                )
            }
        }, navigationIcon = {

        }, actions = {
            if (id != null) {
                if (id == 0) return@MediumTopAppBar
                FilledIconButton(onClick = {
                    //id =-1 it surah list
                    if (id != -1) {
                        if (!itFav) {
                            favorAddAction(id, "")
                        } else {
                            favorDelAction(id, "")
                        }

                    } else {
                        if (!itFav) {
                            if (title != null) {
                                favorAddAction(-1, title)
                            }
                        } else {
                            if (title != null) {
                                favorDelAction(-1, title)
                            }
                        }
                    }
                }) {
                    if (itFav) Icon(
                        Icons.Default.Favorite, contentDescription = null
                    )
                    else Icon(
                        Icons.Outlined.FavoriteBorder, contentDescription = null
                    )

                }
            }
            FilledIconButton(onClick = { uiEvent(UIEvent.SetPlayList(list)) }) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
            }
        }, scrollBehavior = scrollBehavior)
    }) { padding ->

        Column(

            modifier = modifier
                .padding(padding)
                .fillMaxSize()
//            .border(BorderStroke(2.dp , Color.Black))
        ) {
            LazyColumn {
//            item {
//                Button(onClick = { uiEvent(UIEvent.SetPlayList(list)) }) {
//                    Text(text = "تشغل القائمة كاملة")
//                }
//            }
                items(items = quranItem, key = { i -> i.index }) {
                    Item(main = it.surah,
                        text2 = it.artist,
                        text3 = it.moshaf,
                        modifier = Modifier.clickable { uiEvent(UIEvent.SeekToIndex(it.index)) })
                }
                item {
                    Spacer(modifier = modifier.height(30.dp))
                }
            }

        }
    }

}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.Title(
    title: String,
    id: Int?,
    animatedVisibilityScope: AnimatedContentScope,
    moshaf: String?,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        overflow = TextOverflow.Clip,
        modifier = modifier.sharedElement(
            rememberSharedContentState(key = if (id == -1) "title$title" + "null" else "title$title${moshaf}"),
            animatedVisibilityScope = animatedVisibilityScope
        )
    )
}


//
//@Preview(showBackground = true)
//@Composable
//fun
//
//        playlistPre() {
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
////        QuranItem(1 , "www" , "ssssssss" , "dd".toUri() , 0 , false) ,
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
//
//    ListMp(quranItem = list ,
//        title = "title" ,
//        uiEvent = {} ,
//        id = 0 ,
//        favorAddAction = { i: Int , s: String -> } ,
//        favorDelAction =     { i: Int , s: String -> })
//}