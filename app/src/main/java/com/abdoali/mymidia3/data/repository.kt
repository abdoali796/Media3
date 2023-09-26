package com.abdoali.mymidia3.data

import android.net.Uri
import android.util.Log
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Reciter
import com.abdoali.mymidia3.Timer
import com.abdoali.playservice.MediaServiceHandler
import com.abdoali.playservice.MediaStateAbdo
import com.abdoali.playservice.PlayerEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

interface Repository {
    val elapsedTime: StateFlow<Long>
    val isTimerOn: StateFlow<Boolean>
    val shuffle: StateFlow<Boolean>
    val repeat: StateFlow<Boolean>
    val isPlaying: StateFlow<Boolean>
    val sura: StateFlow<List<String>>
    val artistsList: StateFlow<List<Reciter>>
    val list: StateFlow<List<QuranItem>>
    val localList: StateFlow<List<QuranItem>>
    val progress: StateFlow<Float>
    val progressString: StateFlow<String>
    val title: StateFlow<String>
    val artist: StateFlow<String>
    val duration: StateFlow<Long>
    val uri: StateFlow<Uri?>
    val buffering: StateFlow<Boolean>
    fun onUIEvent(uiEvent: UIEvent)
    suspend fun prepareData()
    suspend fun updateUI()
    suspend fun updateProgress()
}

class RepositoryImp @Inject constructor(
    private val mediaServiceHandler: MediaServiceHandler , private val timer: Timer
) : Repository {
    private var resetTimer = timer.isAlarmOn
    override val elapsedTime: StateFlow<Long>
        get() = timer.elapsedTime
    override val isTimerOn: StateFlow<Boolean>
        get() = timer.isAlarmOn
    override val shuffle: StateFlow<Boolean>
        get() = mediaServiceHandler.shuffleMode
    override val repeat: StateFlow<Boolean>
        get() = mediaServiceHandler.repeat
    override val isPlaying: StateFlow<Boolean>
        get() = mediaServiceHandler.isPlay
    override val sura: StateFlow<List<String>>
        get() = mediaServiceHandler.soruhList
    override val artistsList: StateFlow<List<Reciter>>
        get() = mediaServiceHandler.artist
    override val list: StateFlow<List<QuranItem>>
        get() = mediaServiceHandler.quranList
    override val localList: StateFlow<List<QuranItem>>
        get() = mediaServiceHandler.localList

    private var _buffering = MutableStateFlow(false)
    override val buffering: StateFlow<Boolean>
        get() = _buffering
    private var _progress = MutableStateFlow(0f)
    override val progress: StateFlow<Float>
        get() = _progress
    private var _progressString = MutableStateFlow("00:00")
    override val progressString: StateFlow<String>
        get() = _progressString

    private var _title = MutableStateFlow("")
    override val title: StateFlow<String>
        get() = _title
    private var _artist = MutableStateFlow("")
    override val artist: StateFlow<String>
        get() = _artist

    private var _duration = MutableStateFlow(0L)
    override val duration: StateFlow<Long>
        get() = _duration
    private var _url = MutableStateFlow<Uri?>(null)
    override val uri: StateFlow<Uri?>
        get() = _url

    override fun onUIEvent(uiEvent: UIEvent) {

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

            is UIEvent.Shuffle -> mediaServiceHandler.onPlayerEvent(
                PlayerEvent.Shuffle(
                    shuffle = uiEvent.shuffle
                )
            )

            is UIEvent.Timer -> {
                timer.setTimeSelected(uiEvent.time)
                timer.setAlarm(! resetTimer.value)
            }

            is UIEvent.SetPlayList -> mediaServiceHandler.onPlayerEvent(
                PlayerEvent.SetPlayList(
                    uiEvent.list
                )
            )

            is UIEvent.Repeat -> mediaServiceHandler.onPlayerEvent(
                playerEvent = PlayerEvent.Repeat(
                    uiEvent.repeat
                )
            )
        }

    }

    override suspend fun prepareData() {
        mediaServiceHandler.updateData()

    }

    override suspend fun updateUI() {
        mediaServiceHandler.mediaStateAbdo.collect { state ->
            when (state) {
                MediaStateAbdo.Initial -> {
                    _buffering.emit(true)
                }

                MediaStateAbdo.Idle -> _buffering.emit(false)
                is MediaStateAbdo.Ready -> {
                    _buffering.emit(false)
                    _title.emit(state.metadata.title.toString())
                    _artist.emit(state.metadata.artist.toString())
//                        _shuffle.emit(state.shuffleModeEnabled)
                    _url.emit(state.metadata.artworkUri)
                    _duration.emit(state.duration)


                }

                else -> {}
            }
        }

    }

    override suspend fun updateProgress() {

        Log.i("UpdateProgress" , ",UpdataUi")


        while (true) {

            calculateProgressValues(mediaServiceHandler.updateProgress())
            delay(400L)
        }

    }

    private suspend fun calculateProgressValues(currentProgress: Long) {
        _progress.emit(if (currentProgress > 0) (currentProgress.toFloat() / duration.value) else 0f)

        _progressString.emit(formatDuration(currentProgress))
    }

}