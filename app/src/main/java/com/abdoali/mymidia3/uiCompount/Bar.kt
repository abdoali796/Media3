package com.abdoali.mymidia3.uiCompount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdoali.mymidia3.data.UIEvent

@Composable
fun Bar(
    process: Float ,
    processString: String ,
    durationString: String ,
    onUIEvent: (UIEvent) -> Unit
) {
    BarImp(process , processString , durationString , onUIEvent)
}

@Composable
fun BarImp(
    process: Float ,
    processString: String ,
    durationString: String ,
    onUIEvent: (UIEvent) -> Unit ,
    modifier: Modifier = Modifier
) {
    val newProgressValue = remember { mutableFloatStateOf(0f) }
    val useNewProgressValue = remember { mutableStateOf(false) }
    Column(
        modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = modifier.fillMaxWidth() ,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = processString)
            Text(text = durationString)
        }
        Slider(value = if (useNewProgressValue.value) newProgressValue.floatValue else process ,
            onValueChange = {
                useNewProgressValue.value = true
                newProgressValue.floatValue = it
                onUIEvent(UIEvent.UpdateProgress(newProgressValue.floatValue))

            } ,
            onValueChangeFinished = {
                useNewProgressValue.value = false
            }
        )

    }

}

@Preview(showBackground = true)
@Composable
fun BarPreview() {
    BarImp(
        0.5f ,
        "00:55" ,
        "20:00" , {})
}