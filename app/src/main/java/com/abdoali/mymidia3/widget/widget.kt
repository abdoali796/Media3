package com.abdoali.mymidia3.widget


//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.sizeIn
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.outlined.ArrowForwardIos
//import androidx.compose.material.icons.filled.Pause
//import androidx.compose.material.icons.filled.PlayArrow
//import androidx.compose.material.icons.outlined.ArrowBackIos
//import androidx.compose.material.icons.outlined.Repeat
//import androidx.compose.material.icons.outlined.RepeatOn
//import androidx.compose.material.icons.outlined.Shuffle
//import androidx.compose.material.icons.outlined.ShuffleOn
//import androidx.compose.material.icons.outlined.SkipNext
//import androidx.compose.material.icons.outlined.SkipPrevious
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.CompositionLocalProvider
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.platform.LocalLayoutDirection
//import androidx.compose.ui.unit.LayoutDirection
import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.action
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.components.CircleIconButton
import androidx.glance.appwidget.components.FilledButton
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.components.TitleBar
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.unit.ColorProvider
import com.abdoali.mymidia3.MainActivity
import com.abdoali.mymidia3.R
import com.abdoali.mymidia3.data.Repository
import com.abdoali.mymidia3.data.UIEvent

class WidgetBar(private val repository: Repository) : GlanceAppWidget() {


    override suspend fun provideGlance(context: Context, id: GlanceId) {


        provideContent {
            val title by repository.title.collectAsState()
            val play by repository.isPlaying.collectAsState()


            GlanceTheme {

                Scaffold(
                    titleBar = {
                        TitleBar(
                            title = title.ifEmpty { context.getString(R.string.app_name) },
                            startIcon = ImageProvider(R.drawable.baseline_shuffle_24)
                        )
//                        Text(text = title)
                    },
modifier = GlanceModifier.clickable (
    actionStartActivity<MainActivity>()
)
                    ) {
                    BarWidget(
                        isPlaying = play,
                        onUiEvent = repository::onUIEvent
                    )
//                    Row(
//                        modifier = GlanceModifier.fillMaxSize()
//                    ) {
//                        Button(text = ">>", onClick = action {
//                            repository.onUIEvent(UIEvent.PlayPre)
//                        })
//
//                        Button(text = ">", onClick = action {
//                            repository.onUIEvent(UIEvent.PlayPause)
//                        })
//                        Button(text = "<<", onClick = action {
//                            repository.onUIEvent(UIEvent.PlayNext)
//                        })
//
                    //                        action{
//try {
//
//
//    Log.d("WidgetBar","playrepository.isPlaying.value}")
//}catch (e:Exception){
//    Log.d("WidgetBar","error"+e.message.toString())
//}
//
//                        }

                }
            }
        }

    }


}

@Composable
private fun BarWidget(
    onUiEvent: (UIEvent) -> Unit,
    isPlaying: Boolean,
) {
    CompositionLocalProvider(
        LocalLayoutDirection provides LayoutDirection.Ltr
    ) {

        Row(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = GlanceModifier.fillMaxSize(),

            ) {

//                IconButton(onClick = { onUiEvent(UIEvent.Shuffle(! shuffle)) }) {
//                    Icon(
//                        if (! shuffle) Icons.Outlined.Shuffle else Icons.Outlined.ShuffleOn ,
//                        contentDescription = null
//                    )
//                }
            CircleIconButton(
                contentDescription = null,
                contentColor = ColorProvider(MaterialTheme.colorScheme.primary),
                onClick = action {
                    onUiEvent(UIEvent.PlayPre)
                },
                imageProvider = ImageProvider(R.drawable.baseline_skip_previous_24)
            )

            FilledButton(
                text = if (!isPlaying) "pause" else "play",
                onClick = action {
                    onUiEvent(UIEvent.PlayPause)
                },
                icon = if (isPlaying) ImageProvider(androidx.media3.session.R.drawable.media3_icon_play) else ImageProvider(
                    androidx.media3.session.R.drawable.media3_icon_pause
                )
            )
            CircleIconButton(
                contentDescription = "skipNext",
                onClick = action {
                    onUiEvent(UIEvent.PlayNext)
                },
                imageProvider = ImageProvider(R.drawable.baseline_skip_next_24)
            )

//                    Icon(Icons.Outlined.SkipPrevious , contentDescription = null)

//                IconButton(onClick = { onUiEvent(UIEvent.Backward) }) {
//                    Icon(Icons.Outlined.ArrowBackIos , contentDescription = null)
//                }
//                androidx.compose.material3.Button(onClick = { onUiEvent(UIEvent.PlayPause) }) {
//                    Icon(
//                        if (! play) Icons.Default.PlayArrow
//                        else Icons.Default.Pause ,
//                        contentDescription = "play"
//                    )
//
//                }
////                IconButton(onClick = { onUiEvent(UIEvent.Forward) }) {
////                    Icon(Icons.AutoMirrored.Outlined.ArrowForwardIos, contentDescription = null)
////                }
//                IconButton(onClick = { onUiEvent(UIEvent.PlayNext) }) {
//                    Icon(Icons.Outlined.SkipNext , contentDescription = null)
//                }
//                IconButton(onClick = { onUiEvent(UIEvent.Repeat(! repeatOn)) }) {
//                    Icon(
//                        if (repeatOn) Icons.Outlined.RepeatOn
//                        else Icons.Outlined.Repeat ,
//                        contentDescription = "Repeat"
//                    )
//                }
        }
    }
}


class RefreshAction : ActionCallback {

    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters,
    ) {

//        WidgetBar().update(context,glanceId)
    }

}