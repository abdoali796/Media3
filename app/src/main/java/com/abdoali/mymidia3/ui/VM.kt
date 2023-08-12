package com.abdoali.mymidia3.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.mymidia3.Timer
import com.abdoali.playservice.MediaServiceHandler
import com.abdoali.playservice.MediaStateAbdo
import com.abdoali.playservice.PlayerEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class VM @Inject constructor(


    private val mediaServiceHandler: MediaServiceHandler ,
    private val timer: Timer ,
//    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private var resetTimer = timer.isAlarmOn
    var name = timer.elapsedTime
    private var _title = MutableStateFlow("")
    val title: StateFlow<String>
        get() = _title
    private var _artist = MutableStateFlow("")
    val artist: StateFlow<String>
        get() = _artist

    private var _duration = MutableStateFlow(0L)
    val duration: StateFlow<Long>
        get() = _duration


    private var _shuffle = MutableStateFlow(false)
    val shuffle: StateFlow<Boolean>
        get() = _shuffle

    //    var progress1 by savedStateHandle.saveable { mutableStateOf(0f) }
    private var _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float>
        get() = _progress

    //    var progressString1 by savedStateHandle.saveable { mutableStateOf("00:00") }
    private var _progressString = MutableStateFlow("00:00")
    val progressString: StateFlow<String>
        get() = _progressString

    //    var isPlaying by savedStateHandle.saveable { mutableStateOf(false) }
    private var _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean>
        get() = _isPlaying

    private var _url = MutableStateFlow<Uri?>(null)
    val uri: StateFlow<Uri?>
        get() = _url
    val song = mediaServiceHandler.listLocl
    val api = mediaServiceHandler.quranList

    init {
        viewModelScope.launch {

            mediaServiceHandler.setMediaItemListLocal()

            mediaServiceHandler.mediaStateAbdo.collect { state ->
                when (state) {
                    MediaStateAbdo.Initial -> {

                    }

                    is MediaStateAbdo.Ready -> {
                        _title.emit(state.metadata.title.toString())
                        _artist.emit(state.metadata.artist.toString())
                        _shuffle.emit(state.shuffleModeEnabled)
                        _url.emit(state.metadata.artworkUri)
                        _duration.emit(state.duration)
//           _progress.emit(state.progress.toFloat())
                        calculateProgressValues(state.progress)
                        _isPlaying.emit(state.isPlaying)

                    }
                }
            }
            //            mediaServiceHandler.mediaState.collect { state ->
//                when (state) {
//                    is MediaState.Ready -> {
//                        _shuffle.emit(state.shuffleModeEnabled)
//                        _url.emit(state.metadata.artworkUri)
//                        _duration.emit(state.duration.toString())
//                        _progressString.emit(state.metadata.title.toString())
//                    }
//
//                    is MediaState.Playing -> {
//                        _isPlaying.emit(state.isPlaying)
//
//
//                    }
//
//                    is MediaState.Progress -> {
//                        _shuffle.emit(state.shuffleModeEnabled)
//                        _progressString.emit(state.metadata.title.toString())
//                        _progress.emit(state.progress.toFloat())
//                        _url.emit(state.metadata.artworkUri)
//                    }
//
//                    else -> null
//                }
////                timer.elapsedTime.collect {
////                }
//            }
        }


//     val item= MediaItem.fromUri("https://storage.googleapis.com/uamp/The_Kyoto_Connection_-_Wake_Up/04_-_The_Music_In_You.mp3")
//       mediaServiceHandler.addMediaItem(item)

        UpdataUi()
    }

    fun onDataEvent(dataStata: DataEvent) = viewModelScope.launch {
        when (dataStata) {
            DataEvent.AllApi -> mediaServiceHandler.setMediaItemAllOnline()
            DataEvent.Local -> mediaServiceHandler.setMediaItemListLocal()
            DataEvent.FovApi -> mediaServiceHandler.setMediaItemFovOnline()
            DataEvent.NewApi -> mediaServiceHandler.setMediaItemNewOnline()
        }
    }

    fun onUIEvent(uiEvent: UIEvent) = viewModelScope.launch {
        when (uiEvent) {
            UIEvent.PlayPause -> mediaServiceHandler.onPlayerEvent(PlayerEvent.PlayPause)
            UIEvent.PlayNext -> mediaServiceHandler.onPlayerEvent(PlayerEvent.PlayNext)
            UIEvent.PlayPre -> mediaServiceHandler.onPlayerEvent(PlayerEvent.PlayPre)
            UIEvent.Forward -> mediaServiceHandler.onPlayerEvent(PlayerEvent.Forward)
            UIEvent.Backward -> mediaServiceHandler.onPlayerEvent(PlayerEvent.Backward)
            is UIEvent.UpdateProgress -> mediaServiceHandler.onPlayerEvent(
                PlayerEvent.UpdateProgress(
                    uiEvent.newProgress
                )
            )

            is UIEvent.SeekToIndex -> mediaServiceHandler.onPlayerEvent(
                PlayerEvent.SeekToIndex(
                    uiEvent.index
                )
            )

            is UIEvent.Shuffle -> mediaServiceHandler.onPlayerEvent(PlayerEvent.Shuffle(shuffle = uiEvent.shuffle))
            is UIEvent.Timer -> {
                timer.setTimeSelected(uiEvent.time)
                timer.setAlarm(! resetTimer.value)
            }

            else -> null
        }
    }


    private fun UpdataUi() {
//        timer.setTimeSelected(20)
//        timer.setAlarm(! resetTimer.value)
        viewModelScope.launch {
            while (true) {
//                Log.i("UpdataUi","UpdataUi")
                mediaServiceHandler.updataUI(true)
            }


        }
    }

    private suspend fun calculateProgressValues(currentProgress: Long) {
        _progress.emit(if (currentProgress > 0) (currentProgress.toFloat() / duration.value) else 0f)
//        progressString = formatDuration(currentProgress)
        _progressString.emit(formatDuration(currentProgress))

    }

    fun formatDuration(duration: Long): String {
        return if (duration < 3600000) {
            val minutes: Long = TimeUnit.MINUTES.convert(duration , TimeUnit.MILLISECONDS)
            val seconds: Long = (TimeUnit.SECONDS.convert(
                duration , TimeUnit.MILLISECONDS
            ) - minutes * TimeUnit.SECONDS.convert(1 , TimeUnit.MINUTES))
            String.format("%02d:%02d" , minutes , seconds)
        } else {
            val hour: Long = TimeUnit.HOURS.convert(duration , TimeUnit.MILLISECONDS)
            val minutes: Long = TimeUnit.MINUTES.convert(
                duration , TimeUnit.MILLISECONDS
            ) - hour * TimeUnit.MINUTES.convert(60 , TimeUnit.MINUTES)
            val seconds: Long = (TimeUnit.SECONDS.convert(
                duration , TimeUnit.MILLISECONDS
            ) - minutes * TimeUnit.SECONDS.convert(
                1 , TimeUnit.MINUTES
            )) - hour * TimeUnit.SECONDS.convert(1 , TimeUnit.HOURS)
            String.format("%02d:%02d:%02d" , hour , minutes , seconds)
        }

    }


    override fun onCleared() {
        super.onCleared()
    }
}

sealed class UIEvent {
    object PlayPause : UIEvent()
    object Backward : UIEvent()
    object Forward : UIEvent()
    object PlayNext : UIEvent()
    object PlayPre : UIEvent()
    data class SeekToIndex(val index: Int) : UIEvent()
    data class UpdateProgress(val newProgress: Float) : UIEvent()
    data class Shuffle(val shuffle: Boolean) : UIEvent()
    data class Timer(val time: Int) : UIEvent()
}

sealed class DataEvent {
    object Local : DataEvent()
    object NewApi : DataEvent()
    object AllApi : DataEvent()
    object FovApi : DataEvent()
}

sealed class UIState {
    object Initial : UIState()
    object Ready : UIState()
}