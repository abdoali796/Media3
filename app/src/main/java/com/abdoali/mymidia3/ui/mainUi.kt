package com.abdoali.mymidia3.ui

import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.util.Log
import android.util.Size
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.abdoali.mymidia3.data.Song
import com.abdoali.mymidia3.ui.theme.Mymidia3Theme
import com.abdoali.mymidia3.uiCompount.Bar
import com.abdoali.mymidia3.uiCompount.Control
import com.abdoali.mymidia3.uiCompount.MinControlImp
import com.abdoali.mymidia3.uiCompount.PlayUi
import com.abdoali.playservice.R

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MainUi() {
    val vm: VM = hiltViewModel()
    val context = LocalContext.current
    val timer by vm.name.collectAsState()
    val title by vm.title.collectAsState()
    val artists by vm.artist.collectAsState()
    val duration by vm.duration.collectAsState()
    val progress by vm.progress.collectAsState()
    val isPlaying by vm.isPlaying.collectAsState()
    val shuffle by vm.shuffle.collectAsState()
    val progressString by vm.progressString.collectAsState()
    val uri by vm.uri.collectAsState()
    val song = vm.song



    MainUiImp(
        timer.toString() ,
        duration = vm.formatDuration(duration) ,
        progress = progress ,
        isPlaying = isPlaying ,
        progressString = progressString ,
        uri = uri ,
        title = title ,
        artists = artists ,
        song = song ,
        shuffle = shuffle ,
        ac2 = {} ,
        onUIEvent = vm::onUIEvent ,

        )
}

@OptIn(ExperimentalMaterial3Api::class , ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun MainUiImp(
    timer: String ,
    title: String ,
    artists: String ,
    duration: String ,
    progress: Float ,
    isPlaying: Boolean ,
    progressString: String ,
    uri: Uri? ,
    onUIEvent: (UIEvent) -> Unit ,
    ac2: () -> Unit ,
    shuffle: Boolean ,
    song: List<Song> ,

    modifier: Modifier = Modifier
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }


    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()
    Column {

        MinControlImp(
            isPlayerEvent = isPlaying ,
            name = title ,
            onUIEvent = onUIEvent ,
            modifier = Modifier.clickable { openBottomSheet = true })
        LazyColumn(content = {

            items(song.size) {
                Column(
                    Modifier
                        .padding()
                        .clickable {
                            onUIEvent(UIEvent.SeekToIndex(it))
                        }
                ) {
                    Text(text = "=========================================")
                    Text(text = song[it].title)
                    Text(text = song[it].artists)
                    Text(text = song[it].uri.toString())
                }

            }
        })
        MinControlImp(
            isPlayerEvent = isPlaying ,
            name = artists ,
            onUIEvent = onUIEvent ,
            modifier = Modifier.clickable { openBottomSheet = true })


    }
    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false } ,
            content={
              PlayUi(
                  isPlaying = isPlaying ,
                  process = progress ,
                  processString =  progressString,
                  durationString =  duration,
                  shuffle =  shuffle,
                  uri =  uri,
                  onUIEvent =onUIEvent
              )
            })
//            content = {
//                AsyncImage(
//                    model = bitmap ,
//                    contentDescription = null ,
//                    modifier = Modifier.size(158.dp) ,
//                    placeholder = painterResource(
//                        id = R.drawable.plass_foreground
//                    ) ,
//                    error = painterResource(
//                        id = R.drawable.baseline_play_arrow_24
//                    ) ,
//
//
//                    )
//                Bar(
//                    process = progress ,
//                    processString = progressString ,
//                    durationString = duration ,
//                    onUIEvent = onUIEvent
//                )
//                Control(
//                    isPlay = isPlaying ,
//                    shuffle = shuffle ,
//                    onUiEvent = onUIEvent
//                )
//            })
    }

}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Mymidia3Theme {
//        MainUiImp("Android" , {} , {} , {} , {})
    }
}