package com.abdoali.mymidia3.ui.online

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Reciter
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.data.UIEvent

//import com.abdoali.mymidia3.ui.online.ListMp

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MinListTitle(
    title: String,
    titleReciter: List<Reciter>?,
    actionNav: (String, Int) -> Unit,
    actionShowAll: () -> Unit,
    animationSpec: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier,
    any: Float = 0.1f,//of solve Platform declaration clash: The following declarations have the same JVM signature //
) {
    Card(
        modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {

        Column(
            modifier
                .padding(8.dp)
                .fillMaxWidth()
                .padding(1.dp)
        ) {
            Text(text = title, style = MaterialTheme.typography.headlineLarge)
            if (!titleReciter.isNullOrEmpty()) {
with(sharedTransitionScope){
                if (titleReciter.size < 5) {
                    titleReciter.forEach { reciter ->
                        Text(
                            reciter = reciter, animatedContentScope = animationSpec,modifier = modifier.clickable {
                                actionNav(
                                    reciter.name, reciter.id
                                )
                            }
                        )
                    }
                } else {

                    for (i in 0..4) {
                        Text(
                            reciter = titleReciter[i], animatedContentScope = animationSpec,modifier = modifier.clickable {
                                actionNav(
                                    titleReciter[i].name, titleReciter[i].id
                                )
                            },
                        )
                    }

                }
            }}
            Column(
                horizontalAlignment = Alignment.End, modifier = modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = actionShowAll,
                    shape = CardDefaults.shape,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
                ) {
                    Text(text = stringResource(R.string.show_all, title))
                }

            }
        }
    }

}

@Composable
fun MinListTitleItem(
    title: String,
    list: List<QuranItem>,
    onUIEvent: (UIEvent) -> Unit,
    actionShowAll: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (list.isEmpty()) return
    Card(
        modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {

        Column(
            modifier
                .padding(8.dp)
                .fillMaxWidth()
                .padding(1.dp)
        ) {

            Text(text = title, style = MaterialTheme.typography.headlineLarge)

            if (list.size < 10) {
                list.forEach { quranItem: QuranItem ->
                    Text(text = "${quranItem.surah} -> ${quranItem.artist}",
                        modifier
                            .fillMaxWidth()
                            .clickable { onUIEvent(UIEvent.SeekToIndex(quranItem.index)) })
                }
            } else {
                repeat(10) { i ->
                    Text(
                        text = "${list[i].surah}  ${list[i].artist}",
                        modifier
                            .fillMaxWidth()
                            .clickable { onUIEvent(UIEvent.SeekToIndex(list[i].index)) },
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.End, modifier = modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = actionShowAll,
                    shape = CardDefaults.shape,
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
                ) {
                    Text(text = stringResource(R.string.show_all, title))
                }

            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun MinListTitle(
    title: String,
    titleSurh: List<String>,
    actionNav: (String, Int) -> Unit,
    actionShowAll: () -> Unit,
    animationSpec: AnimatedContentScope,
    sharedTransitionScope: SharedTransitionScope,
    modifier: Modifier = Modifier,
) {
    with(sharedTransitionScope) {
        Card(
            modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {

            Column(
                modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .padding(1.dp)
            ) {

                Text(text = title, style = MaterialTheme.typography.headlineLarge)
                if (titleSurh.size < 5) {
                    titleSurh.forEach { surah ->
                        TextSurah(
                            surah = surah,
                            animatedContentScope = animationSpec,
                            modifier = modifier.clickable { actionNav(surah, -1) },
                        )
                    }
                } else {
                    for (i in 1..6) {
                        TextSurah(
                            surah = titleSurh[i],
                            animatedContentScope = animationSpec,
                          modifier =   modifier.clickable { actionNav(titleSurh[i], -1) },
                        )
                    }
                }

                Column(
                    horizontalAlignment = Alignment.End, modifier = modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = actionShowAll,
                        shape = CardDefaults.shape,
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 5.dp)
                    ) {
                        Text(text = stringResource(R.string.show_all, title))
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.Text(
    animatedContentScope: AnimatedContentScope,
    reciter: Reciter,
    modifier: Modifier = Modifier,
) {
    Text(
        text = reciter.name, modifier.sharedElement(
            rememberSharedContentState(key = "title${reciter.name}${reciter.moshaf[0].name}"),
            animatedVisibilityScope = animatedContentScope
        ), style = MaterialTheme.typography.titleLarge
    )
}
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.TextSurah(
    animatedContentScope: AnimatedContentScope,
    surah: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = surah, modifier.sharedElement(
            rememberSharedContentState(key = "title${surah}"+"null"),
            animatedVisibilityScope = animatedContentScope
        ), style = MaterialTheme.typography.titleLarge
    )
}


@Preview(showBackground = true)
@Composable
private fun ListPre() {
//Button(onClick = { /*TODO*/ } , shape = CardDefaults.shape) {
//
//}
}