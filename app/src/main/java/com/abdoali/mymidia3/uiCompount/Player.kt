package com.abdoali.mymidia3.uiCompount

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

import com.abdoali.mymidia3.ui.UIEvent
import com.abdoali.playservice.R

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun PlayUi(
    isPlaying: Boolean ,
    process: Float ,
    processString: String ,
    durationString: String ,
    shuffle: Boolean ,
    uri: Uri? ,
    onUIEvent: (UIEvent) -> Unit ,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var bitmap by remember {
        mutableStateOf<Bitmap?>(null)
    }
    val s ="\"https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/04_-_The_Music_In_You.mp3"
    LaunchedEffect(key1 = uri) {
        try {
            bitmap = uri?.let {
                context.applicationContext.contentResolver.loadThumbnail(
                    it ,
                    Size(100 , 100) ,
                    null
                )
            }
        } catch (e: Exception) {
            Log.i("bitmap" , e.toString())
        }

    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally ,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)

    ) {
        Box {
            AsyncImage(
                model = bitmap ,
                contentDescription = null ,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(
                        10.dp ,
                        10.dp

                    ) ,
                placeholder = painterResource(
                    id = com.bumptech.glide.integration.compose.R.drawable.abc_btn_radio_to_on_mtrl_000
                ) ,
                error = painterResource(
                    id = R.drawable.plass_foreground
                ) ,
                contentScale = ContentScale.FillBounds
            )
            Column(
                verticalArrangement = Arrangement.SpaceBetween ,
                horizontalAlignment = Alignment.CenterHorizontally ,
                modifier = Modifier.fillMaxSize()
            ) {


                AsyncImage(
                    model = bitmap ,
                    contentDescription = null ,
                    modifier = Modifier.size(158.dp) ,
                    placeholder = painterResource(
                        id = com.bumptech.glide.integration.compose.R.drawable.abc_btn_radio_to_on_mtrl_000
                    ) ,
                    error = painterResource(
                        id = R.drawable.plass_foreground
                    )
                )



Column {


                Bar(
                    process = process ,
                    processString = processString ,
                    durationString = durationString ,
                    onUIEvent = onUIEvent
                )
                Control(isPlay = isPlaying , shuffle = shuffle , onUiEvent = onUIEvent)
            }
        }}
    }}