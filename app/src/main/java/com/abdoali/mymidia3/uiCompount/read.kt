package com.abdoali.mymidia3.uiCompount

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.abdoali.datasourece.read.Verse
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ReadUi(
    list: List<Verse>,
    process: Int,
    modifier: Modifier = Modifier,
    title: String,
    artist: String,
) {
    val state = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val local = LocalConfiguration.current.screenHeightDp
    LaunchedEffect(key1 = process) {
        if (process == -1) return@LaunchedEffect
        scope.launch {
            if (process >= 4) {
                state.animateScrollToItem(
                    process - 4)
            }


        }
    }
    val currentView = LocalView.current
    DisposableEffect(key1 = Unit, effect = {
        currentView.keepScreenOn=true

        onDispose {
            currentView.keepScreenOn=false
        }
    })

    val size = local / 2
    Column(

    ) {

        Text(text = title)
        Text(text = artist)
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            state = state,
            modifier = modifier.sizeIn(

                maxHeight = size.dp
            )
        ) {

            items(items = list, key = { it.id }) {
                if (it.id == process) {
                    Text(
                        text = it.text + " {${it.id}}",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.animateContentSize()
                    )
                } else {
                    Text(text = it.text + " {${it.id}}" ,modifier = Modifier.animateContentSize() )
                }
            }
        }
    }
}