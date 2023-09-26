package com.abdoali.mymidia3.composeble

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdoali.mymidia3.data.UIEvent

@Composable
fun SimpleMediaPlayerUI(

    durationString: String ,
    playResourceProvider: () -> Int ,
    progressProvider: () -> Pair<Float , String> ,
    onUiEvent: (UIEvent) -> Unit ,
    modifier: Modifier = Modifier
) {
    val (progress , progressString) = progressProvider()

    Box(
        modifier = modifier
            .padding(16.dp)
            .shadow(elevation = 8.dp , shape = RoundedCornerShape(8.dp))
            .background(Color.LightGray)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            PlayerBar(
                progress = progress ,
                durationString = durationString ,
                progressString = progressString ,
                onUiEvent = onUiEvent
            )
            PlayerControls(
                playResourceProvider = playResourceProvider ,
                onUiEvent = onUiEvent
            )
        }
    }
}

@Preview
@Composable
fun PlayerUIpr() {
    SimpleMediaPlayerUI("00:00" , { 88 } , { 8f to "" } , {})
}