package com.abdoali.mymidia3.data

import android.net.Uri
import android.util.Log
import androidx.core.net.toUri
import com.abdoali.datasourece.QuranItem
import com.abdoali.datasourece.api.Reciter
import com.abdoali.datasourece.api.surahIndex
import com.abdoali.datasourece.api.surahString
import com.abdoali.mymidia3.Timer
import com.abdoali.mymidia3.data.database.favorite.artist.ArtistDatabase
import com.abdoali.mymidia3.data.database.favorite.artist.ArtistID
import com.abdoali.mymidia3.data.database.favorite.item.ItemID
import com.abdoali.mymidia3.data.database.favorite.item.ItemUrlDatabase
import com.abdoali.mymidia3.data.database.favorite.surah.SurahDatabase
import com.abdoali.mymidia3.data.database.favorite.surah.SurahID
import com.abdoali.playservice.MediaServiceHandler
import com.abdoali.playservice.MediaStateAbdo
import com.abdoali.playservice.PlayerEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface Repository {
    val elapsedTime: StateFlow<Long>
    val isTimerOn: StateFlow<Boolean>
    val shuffle: StateFlow<Boolean>
    val repeat: StateFlow<Boolean>
    val isPlaying: StateFlow<Boolean>
    val sura: StateFlow<List<String>>
    val currentMediaItemIndex: StateFlow<Int>

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
    val favArtist: StateFlow<List<Reciter>>
    val favSurah: StateFlow<List<String>>
    val favItem: StateFlow<List<QuranItem>>

    fun onUIEvent(uiEvent: UIEvent)
    suspend fun prepareData()
    suspend fun updateUI()
    suspend fun updateProgress()

    suspend fun addArtistFav(id: Int)
    suspend fun deleteArtistFav(id: Int)
    suspend fun addSurahFav(string: String)
    suspend fun deleteSurahFav(string: String)

    suspend fun addItemURlFav(uri: Uri)
    suspend fun addItemIndexFav(index: Int)
    fun itFavItem(id: Int): Boolean

    suspend fun deleteItemFav(id: Int)

    suspend fun getFavArtist()
    suspend fun getFavSurah()
    suspend fun getFAVItem()
}

class RepositoryImp @Inject constructor(
    private val mediaServiceHandler: MediaServiceHandler ,
    private val timer: Timer ,
    private val artistDatabase: ArtistDatabase ,
    private val surahDatabase: SurahDatabase ,
    private val itemUrlDatabase: ItemUrlDatabase
) : Repository {
    private var resetTimer = timer.isAlarmOn

    override val currentMediaItemIndex: StateFlow<Int>
        get() = mediaServiceHandler.currentMediaItemIndex
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
    private val _favArtist = MutableStateFlow<List<Reciter>>(listOf())
    override val favArtist: StateFlow<List<Reciter>>
        get() = _favArtist
    private val _favSurah = MutableStateFlow<List<String>>(listOf())
    override val favSurah: StateFlow<List<String>>
        get() = _favSurah
    private val _favItem = MutableStateFlow<List<QuranItem>>(emptyList())
    override val favItem: StateFlow<List<QuranItem>>
        get() = _favItem
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

    override suspend fun addArtistFav(id: Int) {
        artistDatabase.artistDao().insertID(id = ArtistID(id))
    }

    override suspend fun deleteArtistFav(id: Int) {
        artistDatabase.artistDao().deleteArtist(id)
    }

    override suspend fun addSurahFav(string: String) {
        val id = surahIndex(string)
        surahDatabase.surahDao().insertID(SurahID(id))
    }

    override suspend fun deleteSurahFav(string: String) {
        val id = surahIndex(string)
        surahDatabase.surahDao().deleteArtist(id)
    }

    override suspend fun addItemURlFav(uri: Uri) {
        itemUrlDatabase.itemDao().item(ItemID(uri = uri.toString()))
    }

    override suspend fun addItemIndexFav(index: Int) {
        val itemID = list.value.find { index == it.index }
        if (itemID != null) {
            itemUrlDatabase.itemDao().item(
                ItemID(
                    uri = itemID.uri
                        .toString()
                )
            )
        }

    }

    override fun itFavItem(id: Int): Boolean {
        val find = _favItem.value.find { it.index == currentMediaItemIndex.value }
        Log.i("  itFavItem" , "reps $find")
        return find != null
    }

    override suspend fun deleteItemFav(id: Int) {
        val quranItem = _favItem.value.find { it.index == id }
        itemUrlDatabase.itemDao().deleteItem(quranItem?.uri.toString())
    }

    override suspend fun getFavArtist() {

        artistDatabase.artistDao().getAllArtist().collect { id ->
            val reciters =
                id.mapNotNull { artistsList.value.find { reciter -> it == reciter.id } }

            _favArtist.update { reciters }
        }

    }

    override suspend fun getFavSurah() {
        surahDatabase.surahDao().getAllSurah().collect { id ->
            val surah = id.map { surahString(it) }
            _favSurah.update { surah }
        }
    }

    override suspend fun getFAVItem() {
        itemUrlDatabase.itemDao().getALLItem().collect { string ->
            val uri = string.map { it.uri.toUri() }
            val item = uri.mapNotNull { uri1 ->
                list.value.find { quranItem: QuranItem -> quranItem.uri == uri1 }

            }


            _favItem.update { item }
        }
    }

    private suspend fun calculateProgressValues(currentProgress: Long) {
        _progress.emit(if (currentProgress > 0) (currentProgress.toFloat() / duration.value) else 0f)

        _progressString.emit(formatDuration(currentProgress))
    }

}