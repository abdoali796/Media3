package com.abdoali.playservice

import android.annotation.SuppressLint
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.PlaybackParameters
import androidx.media3.common.Player
import androidx.media3.common.Tracks
import androidx.media3.exoplayer.ExoPlayer
import com.abdoali.datasourece.DataSources
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Reciter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MediaServiceHandler @Inject constructor(
    private val player: ExoPlayer ,

    private val dataSources: DataSources
) : Player.Listener {

    private val _mediaState = MutableStateFlow<MediaState>(MediaState.Initial)
    val mediaState = _mediaState.asStateFlow()
    private var job: Job? = null

    private var _list =
        MutableStateFlow<List<Int>>(listOf())
    private var index = 0
    private var isPlayList = MutableStateFlow(false)
    private var _mediaStateAbdo = MutableStateFlow<MediaStateAbdo>(MediaStateAbdo.Initial)
    val mediaStateAbdo: StateFlow<MediaStateAbdo>
        get() = _mediaStateAbdo

//    val listLocl = contentResolverHelper.getAudioData()

    private val _quranList = MutableStateFlow<List<QuranItem>>(emptyList())
    val quranList: StateFlow<List<QuranItem>>
        get() = _quranList


    private val _localList = MutableStateFlow<List<QuranItem>>(emptyList())
    val localList: StateFlow<List<QuranItem>>
        get() = _localList


    private val _artist = MutableStateFlow<List<Reciter>>(emptyList())
    val artist: StateFlow<List<Reciter>>
        get() = _artist

    private val _shuffleMode = MutableStateFlow(false)
    val shuffleMode: StateFlow<Boolean>
        get() = _shuffleMode

    private val _isPlay = MutableStateFlow(false)
    val isPlay: StateFlow<Boolean>
        get() = _isPlay

    val test = MutableStateFlow("")
//    private var _progress = MutableStateFlow(0L)
//    val progress: StateFlow<Long>
//        get() = _progress

    init {
        player.addListener(this)
        job = Job()
    }

    fun updateProgress() = player.currentPosition


    suspend fun addMediaItem() {

            player.addMediaItems(prepareMetaData())

            player.prepare()


    }
    suspend fun addMediaItemLocal() {

        player.addMediaItems(prepareMetaDataLocal())
        player.prepare()
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    suspend fun onPlayerEvent(playerEvent: PlayerEvent) {


        when (playerEvent) {
            PlayerEvent.Backward -> player.seekBack()
            PlayerEvent.Forward -> player.seekForward()
            PlayerEvent.PlayPause -> {
                if (_mediaStateAbdo.value == MediaStateAbdo.Idle) {
                    player.prepare()
                    Log.i("onPlaybackStateAbdoali" , "player.prepare()")
                }
                if (player.isPlaying) {
                    player.pause()

//                    stopProgressUpdate()
                } else {
                    player.play()
                    _mediaState.value = MediaState.Playing(isPlaying = true)

                }
            }

            PlayerEvent.PlayNext -> {
                if (isPlayList.value && index < _list.value.size - 1) {
                    player.seekTo(_list.value[index] , 0L)
                    index ++
                } else {
                    player.seekToNextMediaItem()
                }
            }

            PlayerEvent.PlayPre -> {
                if (isPlayList.value && index < _list.value.size - 1 && index != 0) {
                    player.seekTo(_list.value[index] , 0L)
                    index --
                } else {
                    player.seekToPreviousMediaItem()
                }
            }

            PlayerEvent.Stop -> stopProgressUpdate()

            PlayerEvent.Kill -> player.pause()
            is PlayerEvent.Shuffle -> player.shuffleModeEnabled = playerEvent.shuffle
            is PlayerEvent.SeekToIndex -> {
                player.seekTo(playerEvent.index , 0)
                player.play()
                if (! _list.value.contains(playerEvent.index)) isPlayList.value = false

            }

            is PlayerEvent.UpdateProgress -> player.seekTo((player.duration * playerEvent.newProgress).toLong())

            is PlayerEvent.SetPlayList -> preparePlayList(playerEvent.list)
        }
    }

    private fun preparePlayList(intList: List<Int>) {
        index = 0
        isPlayList.value = true
        _list.value = intList


    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters) {
        super.onPlaybackParametersChanged(playbackParameters)
        Log.i("playbackParameters" , "playbackParameters.pitch")


    }

    private fun stopProgressUpdate() {
        job?.cancel()

        _mediaState.value = MediaState.Playing(isPlaying = false)
    }

    override fun onTracksChanged(tracks: Tracks) {

//        Log.i("onPlaybackStateAbdoali","onTracksChanged$tracks")
        super.onTracksChanged(tracks)

    }

    override fun onShuffleModeEnabledChanged(shuffleModeEnabled: Boolean) {
        super.onShuffleModeEnabledChanged(shuffleModeEnabled)
        _shuffleMode.value = shuffleModeEnabled
    }

    override fun onEvents(player: Player , events: Player.Events) {

        super.onEvents(player , events)
    }

    override fun onMediaItemTransition(mediaItem: MediaItem? , reason: Int) {


        if (reason == 1 && index < _list.value.size - 1) {
            player.seekTo(_list.value[index] , 0L)
            index ++

        } else {
            super.onMediaItemTransition(mediaItem , reason)
        }
    }


    @SuppressLint("SwitchIntDef")
    override fun onPlaybackStateChanged(playbackState: Int) {
        Log.i("onPlaybackStateAbdoali" , "playbackState$playbackState")
        when (playbackState) {
            ExoPlayer.STATE_IDLE -> {
                _mediaStateAbdo.tryEmit(MediaStateAbdo.Idle)
            }

            ExoPlayer.STATE_BUFFERING -> _mediaStateAbdo.tryEmit(
                MediaStateAbdo.Initial
            )

            ExoPlayer.STATE_READY -> _mediaStateAbdo.tryEmit(
                MediaStateAbdo.Ready(
                    metadata = player.mediaMetadata ,
                    duration = player.duration
                )
            )

            ExoPlayer.STATE_ENDED -> player.seekTo(0 , 0L)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onIsPlayingChanged(isPlaying: Boolean) {
        super.onIsPlayingChanged(isPlaying)
        _isPlay.value = isPlaying
//
//        if ( mediaStateAbdo.value== MediaStateAbdo.Ready(
//            player.mediaMetadata ,player.duration
//        )) {
//       if (isPlaying) serviceControl.startService() else serviceControl.stopService()
//        }
    }

    override fun onIsLoadingChanged(isLoading: Boolean) {
        super.onIsLoadingChanged(isLoading)
        Log.i("onIsLoadingChanged" , isLoading.toString())
    }

    private suspend fun prepareMetaDataLocal(): List<MediaItem> {
        val date = dataSources.gitLocal()
_list
        _localList.emit(date)
        return date.map { item: QuranItem ->

            val metadata =
                MediaMetadata.Builder().setArtist(item.artist).setDisplayTitle(item.surah)
                    .setArtworkUri(item.uri).build()
            MediaItem.Builder().setMediaMetadata(metadata).setUri(item.uri).build()

        }

    }
    private suspend fun prepareMetaData(): List<MediaItem> {


        return withContext(Dispatchers.IO){
            val date =  dataSources.gitContent()
            _artist.emit(dataSources.getArtist())
            _quranList.emit(date)
Log.i("withContext",date.size.toString())
            date.map { item: QuranItem ->
            if (item.isLocal) {
                val metadata =
                    MediaMetadata.Builder().setArtist(item.artist).setDisplayTitle(item.surah)
                        .setArtworkUri(item.uri).build()
                MediaItem.Builder().setMediaMetadata(metadata).setUri(item.uri).build()
            } else {
                MediaItem.fromUri(item.uri)
            }}
        }

    }

//    private suspend fun prepareMetaData(): List<MediaItem> {
//        delay(1000L)
//        val date = dataSources.gitContent()
//        _artist.emit(dataSources.getArtist())
//        _quranList.emit(date)
//        return date.map { item: QuranItem ->
//            if (item.isLocal) {
//                val metadata =
//                    MediaMetadata.Builder().setArtist(item.artist).setDisplayTitle(item.surah)
//                        .setArtworkUri(item.uri).build()
//                MediaItem.Builder().setMediaMetadata(metadata).setUri(item.uri).build()
//            } else {
//                MediaItem.fromUri(item.uri)
//            }
//        }
//
//    }

}

sealed class MediaState {
    object Initial : MediaState()
    data class Ready(
        val duration: Long , val metadata: MediaMetadata , val shuffleModeEnabled: Boolean
    ) : MediaState()

    data class Progress(
        val progress: Long , val metadata: MediaMetadata , val shuffleModeEnabled: Boolean
    ) : MediaState()

    data class Buffering(val progress: Long) : MediaState()
    data class Playing(val isPlaying: Boolean) : MediaState()
    data class Info(val isShuffle: Boolean) : MediaState()
}

sealed class MediaStateAbdo {
    object Idle : MediaStateAbdo()
    object Initial : MediaStateAbdo()
    data class Ready(

        val metadata: MediaMetadata ,

        val duration: Long

    ) : MediaStateAbdo()
}

sealed class PlayerEvent {
    object PlayPause : PlayerEvent()
    object Backward : PlayerEvent()
    object Forward : PlayerEvent()
    object Stop : PlayerEvent()
    object PlayNext : PlayerEvent()
    object PlayPre : PlayerEvent()
    object Kill : PlayerEvent()
    data class SeekToIndex(val index: Int) : PlayerEvent()
    data class Shuffle(val shuffle: Boolean) : PlayerEvent()
    data class UpdateProgress(val newProgress: Float) : PlayerEvent()

    data class SetPlayList(val list: List<Int>) : PlayerEvent()
}
