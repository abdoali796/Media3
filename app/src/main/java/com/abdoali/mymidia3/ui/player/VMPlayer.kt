package com.abdoali.mymidia3.ui.player

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdoali.datasourece.read.AyaTiming
import com.abdoali.datasourece.read.Read
import com.abdoali.mymidia3.data.Repository
import com.abdoali.mymidia3.data.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VMPlayer @Inject constructor(private val repository: Repository) : ViewModel() {


    val buffering = repository.buffering

    val title = repository.title
    private val currencyItem = repository.currentMediaItemIndex
    val progress: StateFlow<Float>
        get() = repository.progress

    val duration = repository.duration
    val processLong = repository.processLong


    val uri = repository.uri
    val progressString = repository.progressString
    val isPlaying = repository.isPlaying
    val shuffle = repository.shuffle
    val repeat = repository.repeat

    var itFov = MutableStateFlow(false)
    var isLocal = MutableStateFlow(false)
        private set


    var read = MutableStateFlow<Read?>(null)
        private set
    private var timing = MutableStateFlow<AyaTiming?>(null)

    var currencyItemAya = MutableStateFlow(0)
        private set
    var showWords= MutableStateFlow(false)
        private set
    val artist: StateFlow<String>
        get() = repository.artist

    init {

        viewModelScope.launch {
            repository.updateProgress()

        }
        updateUi()
    }
fun showWords(){
    showWords.tryEmit(!showWords.value)
}
    fun onUIEven(uiEvent: UIEvent) = repository.onUIEvent(uiEvent)

    fun addFav() {
        viewModelScope.launch {
            if (!itFov.value) {
                repository.addItemIndexFav(currencyItem.value)
            } else {
                repository.deleteItemFav(currencyItem.value)
            }
            itFav()
        }

    }

    private fun itFav() {
        viewModelScope.launch {
            currencyItem.first { int ->
                delay(100)
                itFov.update { repository.itFavItem(int) }
                true
            }
        }
    }

    private fun updateUi() {
        viewModelScope.launch {
            currencyItem.collect { int ->
                delay(100)
                itFov.update { repository.itFavItem(int) }
                isLocal.update { repository.itLocal(int) }
                read.update { repository.getQuranWords()?.read }
                timing.update { repository.getQuranWords()?.timing }
                Log.i("  itFavItem", "itFav vm ${itFov.value}")

            }
        }
        detactAyahNum()
    }

    fun download() {
        viewModelScope.launch {
            repository.onUIEvent(UIEvent.Download("-1", "-1"))
        }
    }

    private fun detactAyahNum() {
        viewModelScope.launch {


            processLong.collect { progressTime ->
                Log.i("timingAya", "currPre $progressTime And time ${timing.value}")

                if (timing.value.isNullOrEmpty()) {

                    currencyItemAya.emit(-1)
                    Log.i("timingAya", "currPre $progressTime And tivvvvvvvvvme ${currencyItemAya.value}")

                    return@collect
                }


                val t =
                    timing.value?.find { progressTime <= it.start_time && progressTime >= it.end_time }
                Log.i("timingAya", "currPre $t")

                if (t != null) {
                    currencyItemAya.emit(t.ayah)

                }

                Log.i("timingAya", "fl" + currencyItemAya.value.toString())
            }
        }
    }
}


