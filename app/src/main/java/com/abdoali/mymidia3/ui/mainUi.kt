package com.abdoali.mymidia3.ui

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.abdoali.datasourece.Song
import com.abdoali.mymidia3.ui.theme.Mymidia3Theme
import com.abdoali.mymidia3.uiCompount.MinControlImp
import com.abdoali.mymidia3.uiCompount.PlayUi

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
    val test by vm.api.collectAsState()



    MainUiImp(
       vm.formatDuration( timer),
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
    song: List<com.abdoali.datasourece.Song> ,

    modifier: Modifier = Modifier
) {
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }


    val scope = rememberCoroutineScope()
    val sheetScaffoldState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
        Scaffold (
            bottomBar = {
                MinControlImp(
                    isPlayerEvent = isPlaying ,
                    name = title ,
                    onUIEvent = onUIEvent ,
                    modifier = Modifier.clickable { openBottomSheet = true })
            }
        ){padding->



        LazyColumn(content = {
item { Text(text = timer)  }
            items(song.size) {
                Column(
                    Modifier
                        .padding(paddingValues = padding)
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



    }
    if (openBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { openBottomSheet = false } ,
            sheetState = sheetScaffoldState
            ,
            content={
              PlayUi(
                  title=title,
                  artists=artists,
                  isPlaying = isPlaying ,
                  process = progress ,
                  processString =  progressString,
                  durationString =  duration,
                  shuffle =  shuffle,
                  uri =  uri,
                  onUIEvent =onUIEvent
              )
            })
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Mymidia3Theme {
//        MainUiImp("Android" , {} , {} , {} , {})
    }
}