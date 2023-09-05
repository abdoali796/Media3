package com.abdoali.mymidia3.ui

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Reciter
import com.abdoali.datasourece.api.Surah
import com.abdoali.datasourece.helper.isLocal
import com.abdoali.mymidia3.Timer
import com.abdoali.mymidia3.data.DataEvent
import com.abdoali.mymidia3.data.ServiceRun
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.playservice.MediaServiceHandler
import com.abdoali.playservice.MediaStateAbdo
import com.abdoali.playservice.PlayerEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@HiltViewModel
class VM @Inject constructor(


    private val mediaServiceHandler: MediaServiceHandler ,
    private val timer: Timer ,
    private val savedStateHandle: SavedStateHandle
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

val isTimerOn =timer.isAlarmOn
    val isPlaying: StateFlow<Boolean>
        get() = mediaServiceHandler.isPlay

    private var _url = MutableStateFlow<Uri?>(null)
    val uri: StateFlow<Uri?>
        get() = _url
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    val sura = Surah.sura

    private val _artistList = MutableStateFlow<List<String>>(listOf())
    val artistsList: StateFlow<List<Reciter>>
        get() = mediaServiceHandler.artist

    private val _isServiceStart = MutableStateFlow<ServiceRun>(ServiceRun.Stop)
    val isServiceStart: StateFlow<ServiceRun>
        get() = _isServiceStart

    init {
        Log.i("view module" , "initinitinitinit")
        viewModelScope.launch {

            mediaServiceHandler.addMediaItem()

            mediaServiceHandler.mediaStateAbdo.collect { state ->
                when (state) {
                    MediaStateAbdo.Initial -> {
                        _BUFFERING.emit(true)
                    }

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
                }
            }

        }

        UpdataUi()

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

    fun onSearchTextChange(text: String) {
        _searchText.value = text

    }

    fun onSearch(boolean: Boolean) {
        _isSearching.value = boolean
    }

    private val _list = mediaServiceHandler.quranList
    val itemsFilter = _list

    val itemsFilterSearch = searchText.combine(_list) { text , item ->
        if (text.isBlank()) {
            item
        } else {
            item.filter {
                it.query(text)
            }
        }

    }.stateIn(
        viewModelScope , SharingStarted.WhileSubscribed(10000) , _list.value
    )

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


    private fun QuranItem.query(query: String): Boolean {
        val matching = listOf(
            artist , surah
        )
        return matching.any {
            it.contains(query , ignoreCase = true)
        }
    }

    val local = _list.value.isLocal()

    fun serviceStart() {
        _isServiceStart.value = ServiceRun.Run
        Log.i("startForegroundService" , _isServiceStart.value.toString())

    }

    override fun onCleared() {
        _isServiceStart.value = ServiceRun.Kill
        super.onCleared()
    }
}
