package com.abdoali.mymidia3.uiCompount

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonDefaults.shape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdoali.mymidia3.ui.UIEvent
import com.abdoali.playservice.PlayerEvent

@Composable
fun MinControlImp(
    isPlayerEvent: Boolean,
    name:String,
    onUIEvent: (UIEvent)-> Unit,
    modifier: Modifier =Modifier
) {
Card {


    Row(
        horizontalArrangement = Arrangement.SpaceBetween , modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()

    ) {
        Text(name , fontSize = 30.sp)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally ,
            verticalArrangement = Arrangement.Center ,
            modifier = modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(2.dp , color = Color.Black , CircleShape)
                .clickable(onClick = { onUIEvent(UIEvent.PlayPause) })
        ) {
            Icon(
                imageVector = if (! isPlayerEvent) Icons.Default.PlayArrow else Icons.Default.Pause ,
                contentDescription = null ,
                Modifier.size(36.dp)
            )
        }
    }
}
}

@Preview(showBackground = true)
@Composable
fun MinConPre() {
    MinControlImp(
        true,
        "abdo",
        {}
    )
}