package com.abdoali.mymidia3.uiCompount

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.util.Log
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.abdoali.mymidia3.ui.UIEvent


@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun PlayUi(
    title: String ,
    artists: String ,
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

    LaunchedEffect(key1 = uri) {
        try {
            bitmap = uri?.let {
                context.applicationContext.contentResolver.loadThumbnail(
                    it , Size(100 , 100) , null
                )


            }
        } catch (e: Exception) {
            Log.i("bitmap" , e.toString())
        }

    }
    Column(
        verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally ,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)


    ) {
        Box {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {

                AsyncImage(
                    model = bitmap ,
                    contentDescription = null ,
                    modifier = modifier
                        .fillMaxSize()
                        .blur(14.dp) ,
                    placeholder = painterResource(
                        id = com.abdoali.playservice.R.drawable.plass_foreground
                    ) ,
                    error = painterResource(
                        id = com.abdoali.playservice.R.drawable.plass_foreground
                    )
                )
            } else {
                bitmap?.let { LegacyBlurImage(it) }
            }


            Column(
                verticalArrangement = Arrangement.SpaceBetween ,
                horizontalAlignment = Alignment.CenterHorizontally ,
                modifier = Modifier.fillMaxSize()
            ) {


                ImageAudoi(uri = uri , process , title = title , artist = artists)




                Column {


                    Bar(
                        process = process ,
                        processString = processString ,
                        durationString = durationString ,
                        onUIEvent = onUIEvent
                    )
                    Control(
                        isPlay = isPlaying ,
                        shuffle = shuffle ,
                        onUiEvent = onUIEvent ,
                        modifier = Modifier.padding(bottom = 20.dp , top = 20.dp)
                    )
                }

            }
        }
    }
}

@Composable
fun BlurImage(
    bitmap: Bitmap ,
    modifier: Modifier = Modifier ,
) {
    if (! isSystemInDarkTheme()) {
        Image(
            bitmap = bitmap.asImageBitmap() ,
            contentDescription = null ,
            contentScale = ContentScale.FillBounds ,
            modifier = modifier.fillMaxSize()
        )
    } else {
        Image(
            bitmap = bitmap.asImageBitmap() ,
            contentDescription = null ,
            contentScale = ContentScale.FillBounds ,
            modifier = modifier.fillMaxSize() ,
            colorFilter = ColorFilter.tint(
                MaterialTheme.colorScheme.background ,
                BlendMode.Darken
            )
        )
    }
}

@Suppress("DEPRECATION")
@Composable
private fun LegacyBlurImage(
    bitmapBlur: Bitmap , blurRadio: Float = 25f , modifier: Modifier = Modifier
) {
    val context = LocalContext.current


    LaunchedEffect(key1 = bitmapBlur) {
        val renderScript = RenderScript.create(context)

        val bitmapAlloc = Allocation.createFromBitmap(renderScript , bitmapBlur)
        ScriptIntrinsicBlur.create(renderScript , bitmapAlloc.element).apply {
            setRadius(blurRadio)
            setInput(bitmapAlloc)
            forEach(bitmapAlloc)
        }
        bitmapAlloc.copyTo(bitmapBlur)
        renderScript.destroy()
    }
    BlurImage(bitmapBlur , modifier)
}