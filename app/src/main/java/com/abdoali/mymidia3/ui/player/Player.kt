package com.abdoali.mymidia3.ui.player

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abdoali.mymidia3.data.formatDuration
import com.abdoali.mymidia3.uiCompount.Bar
import com.abdoali.mymidia3.uiCompount.Control
import com.abdoali.mymidia3.uiCompount.ImageAudoi

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayUi(
//    title: String ,
//    artists: String ,
//    isPlaying: Boolean ,
//    process: Float ,
//    processString: String ,
//    durationString: String ,
//    shuffle: Boolean ,
//    uri: Uri? ,
//    isLocal: Boolean,
//    onUIEvent: (UIEvent) -> Unit ,

    modifier: Modifier = Modifier
) {
    val vm: VMPlayer = hiltViewModel()
    val title by vm.title.collectAsState()
    val artists by vm.artist.collectAsState()
    val process by vm.progress.collectAsState()
    val isPlaying by vm.isPlaying.collectAsState()
    val processString by vm.progressString.collectAsState()
    val shuffle: Boolean by vm.shuffle.collectAsState()
    val uri by vm.uri.collectAsState()
    val duration by vm.duration.collectAsState()
    val itFav by vm.itFov.collectAsState()
    val repeatOn by vm.repeat.collectAsState()
    val buffer by vm.buffering.collectAsState()

//    LaunchedEffect(key1 = uri) {
//        try {
//            Log.i("uribitmap","uri$uri")
//
//            bitmap=uri
////            bitmap = uri?.let {
////                context.applicationContext.contentResolver.loadThumbnail(
////                    it , Size(100 , 100) , null
////                )
////
////
////            }
//        } catch (e: Exception) {
//            Log.i("bitmap" , e.toString())
//        }
//
//    }

    Column(
        verticalArrangement = Arrangement.Center ,
        horizontalAlignment = Alignment.CenterHorizontally ,
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)

    ) {
        Box {
//            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {

//                AsyncImage(
//                    model = bitmap ,
//                    contentDescription = null ,
//                    modifier = modifier
//                        .fillMaxSize()
//                        .blur(14.dp) ,
//                    placeholder = painterResource(
//                        id = com.abdoali.playservice.R.drawable.plass_foreground
//                    ) ,
//                    error = painterResource(
//                        id = com.abdoali.playservice.R.drawable.plass_foreground
//                    )
//                )
//            } else {
//                bitmap?.let { LegacyBlurImage(it) }
//            }

            Column(
                verticalArrangement = Arrangement.SpaceBetween ,
                horizontalAlignment = Alignment.CenterHorizontally ,
                modifier = Modifier.fillMaxSize()
            ) {

                ImageAudoi(
                    uri = uri ,
                    buffer ,
                    title = title ,
                    artist = artists ,
                    isLocal = true
                )

                LaunchedEffect(key1 = process) {
                    Log.i("currentProgress" , process.toString())
                }


                Column {
                    Icon(if (itFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder ,
                        contentDescription
                        = null ,
                        modifier =
                        modifier
                            .clickable
                            { vm.addFav() })
                    Bar(
                        process = process ,
                        processString = processString ,
                        durationString = formatDuration(duration) ,
                        onUIEvent = vm::onUIEven
                    )

                    Control(
                        isPlay = isPlaying ,
                        shuffle = shuffle ,
                        onUiEvent = vm::onUIEven ,
                        repeatOn = repeatOn ,
                        modifier = Modifier.padding(bottom = 20.dp , top = 20.dp)
                    )
                    Spacer(modifier = modifier.height(20.dp))
                }

            }
        }
    }
}

//@Composable
//fun BlurImage(
//    bitmap: Bitmap ,
//    modifier: Modifier = Modifier ,
//) {
//    if (! isSystemInDarkTheme()) {
//        Image(
//            bitmap = bitmap.asImageBitmap() ,
//            contentDescription = null ,
//            contentScale = ContentScale.FillBounds ,
//            modifier = modifier.fillMaxSize()
//        )
//    } else {
//        Image(
//            bitmap = bitmap.asImageBitmap() ,
//            contentDescription = null ,
//            contentScale = ContentScale.FillBounds ,
//            modifier = modifier.fillMaxSize() ,
//            colorFilter = ColorFilter.tint(
//                MaterialTheme.colorScheme.background ,
//                BlendMode.Darken
//            )
//        )
//    }
//}
//
//@Suppress("DEPRECATION")
//@Composable
//private fun LegacyBlurImage(
//    bitmapBlur: Bitmap , blurRadio: Float = 25f , modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//
//
//    LaunchedEffect(key1 = bitmapBlur) {
//        val renderScript = RenderScript.create(context)
//
//        val bitmapAlloc = Allocation.createFromBitmap(renderScript , bitmapBlur)
//        ScriptIntrinsicBlur.create(renderScript , bitmapAlloc.element).apply {
//            setRadius(blurRadio)
//            setInput(bitmapAlloc)
//            forEach(bitmapAlloc)
//        }
//        bitmapAlloc.copyTo(bitmapBlur)
//        renderScript.destroy()
//    }
//    BlurImage(bitmapBlur , modifier)
//}