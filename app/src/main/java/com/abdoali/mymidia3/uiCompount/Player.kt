package com.abdoali.mymidia3.uiCompount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abdoali.mymidia3.ui.VM


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
    val vm: VM = hiltViewModel()
    val title by vm.title.collectAsState()
    val artists by vm.artist.collectAsState()
    val process by vm.progress.collectAsState()
    val isPlaying by vm.isPlaying.collectAsState()
    val processString by vm.progressString.collectAsState()
    val shuffle: Boolean by vm.shuffle.collectAsState()
    val uri by vm.uri.collectAsState()
    val duration by vm.duration.collectAsState()
    val context = LocalContext.current

    val buffer by vm.BUFFERING.collectAsState()
    var bitmap by remember {
        mutableStateOf<Any?>(null)
    }

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




                Column {


                    Bar(
                        process = process ,
                        processString = processString ,
                        durationString = vm.formatDuration(duration) ,
                        onUIEvent = vm::onUIEvent
                    )
                    Control(
                        isPlay = isPlaying ,
                        shuffle = shuffle ,
                        onUiEvent = vm::onUIEvent ,
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