package com.abdoali.playservice

import android.annotation.SuppressLint
import androidx.media3.common.MediaItem
import androidx.media3.common.MediaMetadata
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.abdoali.datasourece.ApiQuran
import com.abdoali.datasourece.ContentResolverHelper
import com.abdoali.datasourece.Quran
import com.abdoali.datasourece.Song
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MediaServiceHandler @Inject constructor(
    private val player: ExoPlayer ,
    private val contentResolverHelper: ContentResolverHelper ,
    private val apiQuran: ApiQuran
) : Player.Listener {

    private val _mediaState = MutableStateFlow<MediaState>(MediaState.Initial)
    val mediaState = _mediaState.asStateFlow()
    private var job: Job? = null

    private var _mediaStateAbdo = MutableStateFlow<MediaStateAbdo>(MediaStateAbdo.Initial)
    val mediaStateAbdo: StateFlow<MediaStateAbdo>
        get() = _mediaStateAbdo

    val listLocl = contentResolverHelper.getAudioData()
    val quranList = MutableStateFlow<List<Quran>>(emptyList())
    val test = MutableStateFlow("")

    init {
        player.addListener(this)
        job = Job()

    }

    suspend fun updataUI(boolean: Boolean) = job.run {


        _mediaStateAbdo.emit(
            MediaStateAbdo.Ready(
                isPlaying = player.isPlaying ,
                progress = player.currentPosition ,
                metadata = player.mediaMetadata ,

                shuffleModeEnabled = player.shuffleModeEnabled ,
                duration = player.duration
            )
        )

        delay(500)


    }

    fun addMediaItem(mediaItem: MediaItem) {
        player.addMediaItem(mediaItem)
        player.prepare()
    }

    suspend fun setMediaItemAllOnline() {
        quranList.emit(apiQuran.getAllMp3quran())
        player.setMediaItems(prepareAllOnline())
        player.prepare()
    }

    suspend fun setMediaItemNewOnline() {
        quranList.emit(apiQuran.getNewMp3quran())
        player.setMediaItems(prepareNewOnline())
        player.prepare()
    }

    suspend fun setMediaItemFovOnline() {
        quranList.emit(apiQuran.getFovMp3quran())
        player.setMediaItems(prepareFovOnline())
        player.prepare()
    }

    suspend fun setMediaItemListLocal() {
        quranList.emit(listLocl.map { song: Song ->
            Quran(song.index , song.artists , song.title , song.uri)
        })
        player.setMediaItems(prepareMetaDataLocal())

        player.prepare()
    }

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    suspend fun onPlayerEvent(playerEvent: PlayerEvent) {


        when (playerEvent) {
            PlayerEvent.Backward -> player.seekBack()
            PlayerEvent.Forward -> player.seekForward()
            PlayerEvent.PlayPause -> {
                if (player.isPlaying) {
                    player.pause()
                    stopProgressUpdate()
                } else {
                    player.play()
                    _mediaState.value = MediaState.Playing(isPlaying = true)
                    startProgressUpdate()
                }
            }

            PlayerEvent.PlayNext -> player.seekToNextMediaItem()
            PlayerEvent.PlayPre -> player.seekToPreviousMediaItem()
            PlayerEvent.Stop -> stopProgressUpdate()

            PlayerEvent.Kill -> player.pause()
            is PlayerEvent.Shuffle -> player.shuffleModeEnabled = playerEvent.shuffle
            is PlayerEvent.SeekToIndex -> player.seekTo(playerEvent.index , 0)
            is PlayerEvent.UpdateProgress -> player.seekTo((player.duration * playerEvent.newProgress).toLong())
        }
    }


    private suspend fun startProgressUpdate() = job.run {
        while (true) {
            delay(500)

            _mediaState.value = MediaState.Progress(
                player.currentPosition , player.mediaMetadata , player.shuffleModeEnabled
            )
        }
    }

    private fun stopProgressUpdate() {
        job?.cancel()

        _mediaState.value = MediaState.Playing(isPlaying = false)
    }

    @SuppressLint("SwitchIntDef")
    override fun onPlaybackStateChanged(playbackState: Int) {
        when (playbackState) {
            ExoPlayer.STATE_BUFFERING -> _mediaState.value =
                MediaState.Buffering(player.currentPosition)

            ExoPlayer.STATE_READY -> _mediaState.value =
                MediaState.Ready(player.duration , player.mediaMetadata , player.shuffleModeEnabled)
        }
    }

    private fun prepareMetaDataLocal(): List<MediaItem> {
        return contentResolverHelper.getAudioData().map { song: Song ->
            val metadata =
                MediaMetadata.Builder().setArtist(song.artists).setDisplayTitle(song.title)
                    .setArtworkUri(song.uri).build()
            MediaItem.Builder().setMediaMetadata(metadata).setUri(song.uri).build()
        }

    }

    private suspend fun prepareAllOnline(): List<MediaItem> {
        return apiQuran.getAllMp3quran().map { quran: Quran ->
            MediaItem.Builder().setUri(quran.uri).build()
        }

    }

    private suspend fun prepareNewOnline(): List<MediaItem> {
        return apiQuran.getNewMp3quran().map { quran: Quran ->
            MediaItem.Builder().setUri(quran.uri).build()
        }

    }

    private suspend fun prepareFovOnline(): List<MediaItem> {
        return apiQuran.getFovMp3quran().map { quran: Quran ->
            MediaItem.Builder().setUri(quran.uri).build()
        }
    }
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
    object Initial : MediaStateAbdo()
    data class Ready(
        val isPlaying: Boolean ,
        val progress: Long ,
        val metadata: MediaMetadata ,
        val shuffleModeEnabled: Boolean ,
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
}
