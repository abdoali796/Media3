package com.abdoali.mymidia3.uiCompount

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.SyncDisabled
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.abdoali.datasourece.read.Verse
import com.abdoali.mymidia3.R
import kotlinx.coroutines.launch


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
                    process - 4
                )
            }


        }
    }
    val currentView = LocalView.current
    DisposableEffect(key1 = Unit, effect = {
        currentView.keepScreenOn = true

        onDispose {
            currentView.keepScreenOn = false
        }
    })

    val size = local / 2
    Column {

        Text(text = title)
        Text(text = artist)
        Row {
            Text(text = stringResource(R.string.sync_text))
            if (-1 != process) {
                Text(
                    text = stringResource(R.string.active),
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(imageVector = Icons.Default.Sync, contentDescription = null)
            } else {
                Text(text = stringResource(R.string.inactive)
                , color = MaterialTheme.colorScheme.error)
                Icon(
                    imageVector = Icons.Default.SyncDisabled,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }

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
                    Text(text = it.text + " {${it.id}}", modifier = Modifier.animateContentSize())
                }
            }
        }
    }
}