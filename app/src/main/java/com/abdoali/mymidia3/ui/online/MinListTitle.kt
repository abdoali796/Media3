package com.abdoali.mymidia3.ui.online

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Reciter
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.data.UIEvent

//import com.abdoali.mymidia3.ui.online.ListMp

@Composable
fun MinListTitle(
    title: String ,
    titleReciter: List<Reciter>? ,
    actionNav: (String , Int) -> Unit ,
    actionShowAll: () -> Unit ,
    modifier: Modifier = Modifier ,
    any: Float = 0.1f//of solve Platform declaration clash: The following declarations have the same JVM signature //
) {
    Column(
        modifier
            .fillMaxWidth()
            .border(
                1.dp ,
                color = MaterialTheme.colorScheme.onPrimaryContainer ,
                shape = MaterialTheme.shapes.large
            )
            .padding(3.dp)
    ) {
        Text(text = title , style = MaterialTheme.typography.headlineLarge)
        if (! titleReciter.isNullOrEmpty()) {

            if (titleReciter.size < 5) {
                titleReciter.forEach { reciter ->
                    Text(
                        text = reciter.name ,
                        modifier.clickable {
                            actionNav(
                                reciter.name ,
                                reciter.id
                            )
                        } ,
                        style = MaterialTheme.typography.titleLarge)
                }
            } else {
                for (i in 0..5) {
                    Text(
                        text = titleReciter[i].name ,
                        modifier.clickable {
                            actionNav(
                                titleReciter[i].name ,
                                titleReciter[i].id
                            )
                        } ,
                        style = MaterialTheme.typography.titleLarge)
                }

            }
        }
        Button(onClick = actionShowAll) {
            Text(text = stringResource(R.string.show_all , title))
        }
    }


}

@Composable
fun MinListTitleItem(
    title: String ,
    list: List<QuranItem> ,
    onUIEvent: (UIEvent) -> Unit ,
    actionShowAll: () -> Unit ,
    modifier: Modifier = Modifier
) {
    if (list.isEmpty()) return
    Column(
        modifier
            .fillMaxWidth()
            .border(
                1.dp ,
                color = MaterialTheme.colorScheme.onPrimaryContainer ,
                shape = MaterialTheme.shapes.large
            )
            .padding(3.dp)
    ) {
        Text(text = title , style = MaterialTheme.typography.headlineLarge)

        if (list.size < 10) {
            list.forEach { quranItem: QuranItem ->
                Text(
                    text = "${quranItem.surah} -> ${quranItem.artist}" ,
                    modifier
                        .fillMaxWidth()
                        .clickable { onUIEvent(UIEvent.SeekToIndex(quranItem.index)) })
            }
        } else {
            repeat(10) { i ->
                Text(
                    text = "${list[i].surah}  ${list[i].artist}" ,
                    modifier
                        .fillMaxWidth()
                        .clickable { onUIEvent(UIEvent.SeekToIndex(list[i].index)) } ,
                    style = MaterialTheme.typography.titleLarge)
            }
        }

        Button(onClick = actionShowAll) {
            Text(text = stringResource(R.string.show_all , title))
        }
    }
}

@Composable
fun MinListTitle(
    title: String ,
    titleSurh: List<String> ,
    actionNav: (String , Int) -> Unit ,
    actionShowAll: () -> Unit ,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxWidth()
            .border(
                1.dp ,
                color = MaterialTheme.colorScheme.onPrimaryContainer ,
                shape = MaterialTheme.shapes.large
            )
            .padding(3.dp)
    ) {
        Text(text = title , style = MaterialTheme.typography.headlineLarge)
        if (titleSurh.size < 5) {
            titleSurh.forEach { surah ->
                Text(
                    text = surah ,
                    modifier.clickable { actionNav(surah , - 1) } ,
                    style = MaterialTheme.typography.titleLarge)
            }
        } else {
            for (i in 1..6) {
                Text(
                    text = titleSurh[i] ,
                    modifier.clickable { actionNav(titleSurh[i] , - 1) } ,
                    style = MaterialTheme.typography.titleLarge)
            }
        }

        Button(onClick = actionShowAll) {
            Text(text = stringResource(R.string.show_all , title))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ListPre() {
//    val reciter=Reciter
//    MinListTitle(
//        "test" ,
//      titleReciter = Reciter() , null ,
//        { i: String , s: Int -> } , {})
}