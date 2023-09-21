package com.abdoali.mymidia3.ui

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.datasourece.api.Reciter
import com.abdoali.datasourece.api.surah
import com.abdoali.mymidia3.Timer
import com.abdoali.mymidia3.data.DataEvent
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.playservice.MediaServiceHandler
import com.abdoali.playservice.MediaStateAbdo
import com.abdoali.playservice.PlayerEvent
import com.abdoali.playservice.service.ServiceControl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class VM @Inject constructor(


    private val mediaServiceHandler: MediaServiceHandler ,
    private val timer: Timer ,
    private val serviceControl: ServiceControl
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


    //    private var _shuffle = MutableStateFlow(false)
    val shuffle: StateFlow<Boolean>
        get() = mediaServiceHandler.shuffleMode

    private var _BUFFERING = MutableStateFlow(false)
    val BUFFERING: StateFlow<Boolean>
        get() = _BUFFERING

    //    var progress1 by savedStateHandle.saveable { mutableStateOf(0f) }
    private var _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float>
        get() = _progress

    //    var progressString1 by savedStateHandle.saveable { mutableStateOf("00:00") }
    private var _progressString = MutableStateFlow("00:00")
    val progressString: StateFlow<String>
        get() = _progressString

    val isTimerOn = timer.isAlarmOn
    val isPlaying: StateFlow<Boolean>
        get() = mediaServiceHandler.isPlay

    private var _url = MutableStateFlow<Uri?>(null)
    val uri: StateFlow<Uri?>
        get() = _url

    val sura = surah()

    private val _artistList = MutableStateFlow<List<String>>(listOf())
    val artistsList: StateFlow<List<Reciter>>
        get() = mediaServiceHandler.artist

    val list = mediaServiceHandler.quranList
val localList=mediaServiceHandler.localList
    //    private val _isServiceStart = MutableStateFlow<ServiceRun>(ServiceRun.Stop)
//    val isServiceStart: StateFlow<ServiceRun>
//        get() = _isServiceStart
    private var isServiceStart = false

    init {
        Log.i("view module" , "initinitinitinit")
        viewModelScope.launch (){


    mediaServiceHandler.addMediaItemLocal()
    mediaServiceHandler.addMediaItem()



            mediaServiceHandler.mediaStateAbdo.collect { state ->
                when (state) {
                    MediaStateAbdo.Initial -> {
                        _BUFFERING.emit(true)
                    }

                    MediaStateAbdo.Idle -> _BUFFERING.emit(false)
                    is MediaStateAbdo.Ready -> {
                        _BUFFERING.emit(false)
                        _title.emit(state.metadata.title.toString())
                        _artist.emit(state.metadata.artist.toString())
//                        _shuffle.emit(state.shuffleModeEnabled)
                        _url.emit(state.metadata.artworkUri)
                        _duration.emit(state.duration)
//           _progress.emit(state.progress.toFloat())
//                        calculateProgressValues(state.progress)
//                        _isPlaying.emit(state.isPlaying)

                    }

                    else -> {}
                }
            }

        }
        startSarvie()
        UpdataUi()

    }

    private fun startSarvie() {
        if (! isServiceStart) {
            serviceControl.startService()
            isServiceStart = true
        }
    }

    fun onDataEvent(dataStata: DataEvent) = viewModelScope.launch {
        when (dataStata) {
//            DataEvent.AllApi -> mediaServiceHandler.setMediaItemAllOnline()
//            DataEvent.Local -> mediaServiceHandler.setMediaItemListLocal()
//            DataEvent.FovApi -> mediaServiceHandler.setMediaItemFovOnline()
            else -> {}
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

            is UIEvent.SetPlayList -> mediaServiceHandler.onPlayerEvent(
                PlayerEvent.SetPlayList(
                    uiEvent.list
                )
            )

            else -> null
        }
    }


    private fun UpdataUi() {
        viewModelScope.launch {
            Log.i("UpdateProgress" , ",UpdataUi")


            while (true) {

                calculateProgressValues(mediaServiceHandler.updateProgress())
                delay(400L)
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


//    val local = _list.value.isLocal()


    override fun onCleared() {
        serviceControl.stopService()
        isServiceStart = false
        super.onCleared()
    }
}
