package com.abdoali.mymidia3.uiCompount

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bumptech.glide.integration.compose.R


@Composable
fun ImageAudoi(
    uri: Uri? ,
    buffer: Boolean ,
    isLocal:Boolean,
    artist: String = "غير محدد" , title: String = "غير محدد" , modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var bitmap by remember {
        mutableStateOf<Any?>(null)
    }

//    val animationFloat= AnimationState(process * 306)
    LaunchedEffect(key1 = uri) {
        try {
            bitmap = if (uri==null){
                 com.abdoali.mymidia3.R.drawable.quran
            }else{
                uri?.let {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        context.applicationContext.contentResolver.loadThumbnail(
                            it ,
                            Size(100 , 100) ,
                            null
                        )
                    } else {
                        TODO("VERSION.SDK_INT < Q")
                    }


                }
            }
        } catch (e: Exception) {
            Log.i("bitmap" , e.toString())

        }

    }
    Column(
        verticalArrangement = Arrangement.Center
    ) {
if(buffer) {
    CircularProgressIndicator(
        modifier.size(200.dp)
    )
}
        AnimatedVisibility(visible =! buffer) {

        AsyncImage(
            model =bitmap ,
            contentDescription = null ,
            modifier = modifier

                .clip(MaterialTheme.shapes.medium)

                .size(200.dp) ,
            placeholder = painterResource(
                id = R.drawable.abc_btn_radio_to_on_mtrl_000
            ) ,
            error = painterResource(
                id = com.abdoali.playservice.R.drawable.plass_foreground
            ) , contentScale = ContentScale.FillBounds

        )}
    }
    Text(text = title , style = MaterialTheme.typography.headlineSmall )
    Text(text = artist , style = MaterialTheme.typography.titleSmall )

}