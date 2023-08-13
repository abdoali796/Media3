package com.abdoali.mymidia3.ui

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.datasourece.Quran
import com.abdoali.mymidia3.Timer
import com.abdoali.mymidia3.data.DataEvent
import com.abdoali.mymidia3.data.UIEvent
import com.abdoali.playservice.MediaServiceHandler
import com.abdoali.playservice.MediaStateAbdo
import com.abdoali.playservice.PlayerEvent
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()


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

        }

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

    fun onSearchTextChange(text: String) {
        _searchText.value = text

    }

    fun onSearch(boolean: Boolean) {
        _isSearching.value = boolean
    }

    private val _list = mediaServiceHandler.quranList
    val itemsFilter = searchText.combine(_list) { text , item ->
        if (text.isBlank()){
item
        }else{
    item.filter {
        it.query(text)
    }
        }

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5999999)
            ,_list.value
    )

    val itemsFilterSearch = searchText.combine(_list) { text , item ->
        if (text.isBlank()){
            listOf(item[0],item[1],item[3])
        }else{
            item.filter {
                it.query(text)
            }
        }

    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(10000)
        ,_list.value
    )
    private fun UpdataUi() {
        viewModelScope.launch {
            while (true) {

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


    fun Quran.query(query: String): Boolean {
        val matching = listOf(
            artists , surah
        )
        return matching.any {
            it.contains(query , ignoreCase = true)
        }
    }


    override fun onCleared() {
        super.onCleared()
    }
}
