package com.abdoali.mymidia3.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.abdoali.datasourece.QuranItem
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.mymidia3.uiCompount.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListMp(
    quranItem: List<QuranItem> ,
    title: String? ,
    uiEvent: (UIEvent) -> Unit ,
    modifier: Modifier = Modifier
) {
    val list =
        remember {
            quranItem.map {
                it.index
            }
        }
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection) ,
        topBar = {
            MediumTopAppBar(
                title = {
                    Row(
                        modifier = modifier.fillMaxWidth() ,
                        horizontalArrangement = Arrangement.SpaceBetween ,

                        ) {

                        if (title != null) {
                            Text(text = title , overflow = TextOverflow.Ellipsis)

                        }
                        FilledIconButton(onClick = { uiEvent(UIEvent.SetPlayList(list)) }) {
                            Icon(Icons.Default.PlayArrow , contentDescription = null)
                        }
                    }
                } , scrollBehavior = scrollBehavior
            )
        }
    ) { padding ->

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
                items(items = quranItem , key = { i -> i.index }) {
                    Item(
                        main = it.surah ,
                        text2 = it.artist ,
                        text3 = it.moshaf ,
                        modifier = Modifier.clickable { uiEvent(UIEvent.SeekToIndex(it.index)) })
                }
                item {
                    Spacer(modifier = modifier.height(30.dp))
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun playlistPre() {
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
    )

    ListMp(
        quranItem = list ,
        title = "title" ,
        uiEvent = {}
    )
}