package com.abdoali.mymidia3.uiCompount

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.outlined.ArrowBackIos
import androidx.compose.material.icons.outlined.ArrowForwardIos
import androidx.compose.material.icons.outlined.Repeat
import androidx.compose.material.icons.outlined.RepeatOn
import androidx.compose.material.icons.outlined.Shuffle
import androidx.compose.material.icons.outlined.ShuffleOn
import androidx.compose.material.icons.outlined.SkipNext
import androidx.compose.material.icons.outlined.SkipPrevious
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import com.abdoali.mymidia3.data.UIEvent

@Composable
fun Control(
    isPlay: Boolean ,
    repeatOn: Boolean ,
    shuffle: Boolean ,
    onUiEvent: (UIEvent) -> Unit ,
    modifier: Modifier = Modifier
) {

    ControlImp(
        play = isPlay ,
        shuffle = shuffle ,
        repeatOn = repeatOn ,
        onUiEvent ,
        modifier = modifier ,
    )
}

@Composable
private fun ControlImp(
    play: Boolean ,
    shuffle: Boolean ,
    repeatOn: Boolean ,
    onUiEvent: (UIEvent) -> Unit ,
    modifier: Modifier = Modifier ,

    ) {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Ltr
    ) {

        Row(
            horizontalArrangement = Arrangement.Center ,
            modifier = modifier.fillMaxWidth()
        ) {

            IconButton(onClick = { onUiEvent(UIEvent.Shuffle(! shuffle)) }) {
                Icon(
                    if (! shuffle) Icons.Outlined.Shuffle else Icons.Outlined.ShuffleOn ,
                    contentDescription = null
                )
            }
            IconButton(onClick = { onUiEvent(UIEvent.PlayPre) }) {
                Icon(Icons.Outlined.SkipPrevious , contentDescription = null)
            }
            IconButton(onClick = { onUiEvent(UIEvent.Backward) }) {
                Icon(Icons.Outlined.ArrowBackIos , contentDescription = null)
            }
            Button(onClick = { onUiEvent(UIEvent.PlayPause) }) {
                Icon(
                    if (! play) Icons.Default.PlayArrow
                    else Icons.Default.Pause ,
                    contentDescription = "play"
                )

            }
            IconButton(onClick = { onUiEvent(UIEvent.Forward) }) {
                Icon(Icons.Outlined.ArrowForwardIos , contentDescription = null)
            }
            IconButton(onClick = { onUiEvent(UIEvent.PlayNext) }) {
                Icon(Icons.Outlined.SkipNext , contentDescription = null)
            }
            IconButton(onClick = { onUiEvent(UIEvent.Repeat(! repeatOn)) }) {
                Icon(
                    if (repeatOn) Icons.Outlined.RepeatOn
                    else Icons.Outlined.Repeat ,
                    contentDescription = "Repeat"
                )
            }
        }
    }
}

@Preview(showBackground = true , uiMode = UI_MODE_NIGHT_YES , locale = "ar")
@Composable
private fun ControlPre() {
    ControlImp(
        play = true ,
        shuffle = true ,
        repeatOn = true ,
        onUiEvent = { UIEvent.PlayPre } ,
    )
}